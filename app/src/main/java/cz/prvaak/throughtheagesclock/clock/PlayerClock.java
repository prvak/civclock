package cz.prvaak.throughtheagesclock.clock;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.timer.LimitedTimer;
import cz.prvaak.throughtheagesclock.clock.timer.Timer;
import cz.prvaak.throughtheagesclock.clock.timer.TimerClock;

/**
 * Class for keeping track of how much time remains to a player.
 *
 * Limited upkeep protection period can be enabled by {@link #upkeep(cz.prvaak.throughtheagesclock.TimeInstant)}. Time reserve does not
 * decrease during upkeep period but upkeep timer is running even if the main timer is stopped.
 *
 * Clock can by paused by {@link Clock#pause(cz.prvaak.throughtheagesclock.TimeInstant)} method and resumed by {@link Clock#start(cz.prvaak.throughtheagesclock.TimeInstant)}. Both
 * main and upkeep timers are paused.
 */
public class PlayerClock implements Clock {

	/** Unique id of a player. */
	private final PlayerId playerId;
	/** Counter of elapsed reserve time. */
	private final TimerClock reserveTime;
	/** Counter of elapsed upkeep time. */
	private final TimerClock upkeepTime;
	/**
	 * How long did the reserve clock overlapped with upkeep clock. This time does not count
	 * towards the reserve time.
	 */
	private final TimerClock overlapTime;
	/** Amount of time that should be added after regular turn. */
	private final TimeAmount turnBonusTime;
	/** Whether the clock has been started and not stopped yet. */
	private boolean isStarted;

	/**
	 * Create new clock.
	 *
	 * @param playerId Identification of the player.
	 * @param baseTime How many milliseconds the player initially has.
	 * @param upkeepTime How many milliseconds each upkeep protection has.
	 * @param turnBonusTime How many milliseconds to add after each turn.
	 */
	public PlayerClock(PlayerId playerId, TimeAmount baseTime, TimeAmount upkeepTime,
			TimeAmount turnBonusTime) {
		if (turnBonusTime.isNegative()) {
			throw new IllegalArgumentException("Negative turn bonus time is not allowed!");
		}

		this.playerId = playerId;
		this.reserveTime = new Timer(baseTime);
		this.upkeepTime = new LimitedTimer(upkeepTime);
		this.overlapTime = new LimitedTimer(TimeAmount.EMPTY);
		this.turnBonusTime = turnBonusTime;
	}

	@Override
	public void start(TimeInstant when) {
		reserveTime.start(when);
		reserveTime.addTime(when, overlapTime.getElapsedTime(when));
		overlapTime.restart(when, upkeepTime.getRemainingTime(when));
		isStarted = true;
	}

	@Override
	public void stop(TimeInstant when) {
		reserveTime.stop(when);
		overlapTime.stop(when);
		isStarted = false;
	}

	@Override
	public void pause(TimeInstant when) {
		reserveTime.pause(when);
		upkeepTime.pause(when);
		overlapTime.pause(when);
	}

	@Override
	public void resume(TimeInstant when) {
		reserveTime.resume(when);
		upkeepTime.resume(when);
		overlapTime.resume(when);
	}

	/**
	 * Add given amount of time to the reserve time.
	 *
	 * @param when Current time in milliseconds.
	 * @param amount How many milliseconds to add to the reserve time. If the value is negative
	 */
	public void addReserveTime(TimeInstant when, TimeAmount amount) {
		reserveTime.addTime(when, amount);
	}

	/**
	 * Start the upkeep protection period.
	 *
	 * The protection period timer cannot be stopped, only paused. Calling this method again
	 * restarts the timer with initial value.
	 *
	 * @param when Current time in milliseconds.
	 */
	public void upkeep(TimeInstant when) {
		upkeepTime.restart(when);
		if (isStarted) {
			reserveTime.addTime(when, overlapTime.getElapsedTime(when));
			overlapTime.restart(when, upkeepTime.getRemainingTime(when));
		}
	}

	/**
	 * Add predefined amount of time to the remaining time.
	 * The amount is defined in constructor.
	 * @param when Current time in milliseconds.
	 */
	public void addTurnBonusTime(TimeInstant when) {
		addReserveTime(when, turnBonusTime);
	}

	/**
	 * How many milliseconds of reserve time are remaining.
	 *
	 * @param when Current time in milliseconds.
	 * @return Remaining reserve time in milliseconds.
	 */
	public TimeAmount getRemainingReserveTime(TimeInstant when) {
		TimeAmount remainingReserve = reserveTime.getRemainingTime(when);
		TimeAmount upkeepOverlap = overlapTime.getElapsedTime(when);
		return remainingReserve.add(upkeepOverlap);
	}

	/**
	 * How many milliseconds of upkeep protection period are remaining.
	 *
	 * @param when Current time in milliseconds.
	 * @return Remaining upkeep protection in milliseconds.
	 */
	public TimeAmount getRemainingUpkeepTime(TimeInstant when) {
		return upkeepTime.getRemainingTime(when);
	}

	/** Get id of player whose clock this is. */
	public PlayerId getPlayerId() {
		return playerId;
	}
}
