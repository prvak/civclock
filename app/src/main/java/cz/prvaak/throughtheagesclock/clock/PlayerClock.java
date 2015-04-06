package cz.prvaak.throughtheagesclock.clock;

import cz.prvaak.throughtheagesclock.clock.Clock;
import cz.prvaak.throughtheagesclock.clock.timer.LimitedTimer;
import cz.prvaak.throughtheagesclock.clock.timer.Timer;
import cz.prvaak.throughtheagesclock.clock.timer.TimerClock;

/**
 * Class for keeping track of how much time remains to a player.
 *
 * Limited upkeep protection period can be enabled by {@link #upkeep(long)}. Time reserve does not
 * decrease during upkeep period but upkeep timer is running even if the main timer is stopped.
 *
 * Clock can by paused by {@link #pause(long)} method and resumed by {@link #start(long)}. Both
 * main and upkeep timers are paused.
 */
public class PlayerClock implements Clock {

	/** Counter of elapsed reserve time. */
	private final TimerClock reserveTime;
	/** Counter of elapsed upkeep time. */
	private final TimerClock upkeepTime;
	/**
	 * How long did the reserve clock overlapped with upkeep clock. This time does not count
	 * towards the reserve time.
	 */
	private final TimerClock overlapTime;
	/** Whether the clock has been started and not stopped yet. */
	private boolean isStarted;

	/**
	 * Create new clock.
	 *
	 * @param baseTime How many milliseconds the player initially has.
	 * @param upkeepTime How many milliseconds each upkeep protection has.
	 */
	public PlayerClock(long baseTime, long upkeepTime) {
		this.reserveTime = new Timer(baseTime);
		this.upkeepTime = new LimitedTimer(upkeepTime);
		this.overlapTime = new LimitedTimer(0L);
	}

	@Override
	public void start(long when) {
		reserveTime.start(when);
		reserveTime.addTime(overlapTime.getElapsedTime(when));
		overlapTime.restart(when, upkeepTime.getRemainingTime(when));
		isStarted = true;
	}

	@Override
	public void stop(long when) {
		reserveTime.stop(when);
		overlapTime.stop(when);
		isStarted = false;
	}

	@Override
	public void pause(long when) {
		reserveTime.pause(when);
		upkeepTime.pause(when);
		overlapTime.pause(when);
	}

	@Override
	public void resume(long when) {
		reserveTime.resume(when);
		upkeepTime.resume(when);
		overlapTime.resume(when);
	}

	/**
	 * Add given amount of time to the reserve time.
	 *
	 * @param amount How many milliseconds to add to the reserve time. If the value is negative
	 *	the reserve time is reduced.
	 */
	public void addReserveTime(long amount) {
		reserveTime.addTime(amount);
	}

	/**
	 * Start the upkeep protection period.
	 *
	 * The protection period timer cannot be stopped, only paused. Calling this method again
	 * restarts the timer with initial value.
	 *
	 * @param when Current time in milliseconds.
	 */
	public void upkeep(long when) {
		upkeepTime.restart(when);
		if (isStarted) {
			reserveTime.addTime(overlapTime.getElapsedTime(when));
			overlapTime.restart(when, upkeepTime.getRemainingTime(when));
		}
	}

	/**
	 * How many milliseconds of reserve time are remaining.
	 *
	 * @param when Current time in milliseconds.
	 * @return Remaining reserve time in milliseconds.
	 */
	public long getRemainingReserveTime(long when) {
		long remainingReserve = reserveTime.getRemainingTime(when);
		long upkeepOverlap = overlapTime.getElapsedTime(when);
		return remainingReserve + upkeepOverlap;
	}

	/**
	 * How many milliseconds of upkeep protection period are remaining.
	 *
	 * @param when Current time in milliseconds.
	 * @return Remaining upkeep protection in milliseconds.
	 */
	public long getRemainingUpkeepTime(long when) {
		return upkeepTime.getRemainingTime(when);
	}
}
