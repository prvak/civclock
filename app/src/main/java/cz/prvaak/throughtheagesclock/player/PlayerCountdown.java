package cz.prvaak.throughtheagesclock.player;

import cz.prvaak.throughtheagesclock.clock.PausableClock;
import cz.prvaak.throughtheagesclock.clock.StoppableClock;
import cz.prvaak.throughtheagesclock.clock.countdown.Countdown;
import cz.prvaak.throughtheagesclock.clock.countdown.CountdownClock;
import cz.prvaak.throughtheagesclock.clock.countdown.adapter.LimitedCountdown;
import cz.prvaak.throughtheagesclock.clock.timer.Timer;
import cz.prvaak.throughtheagesclock.clock.timer.TimerClock;
import cz.prvaak.throughtheagesclock.clock.timer.adapter.LimitedTimer;

/**
 * Class for keeping track of how much time remains to a player.
 *
 * Limited upkeep protection period can be enabled by {@link #upkeep(long)}. Time reserve does not
 * decrease during upkeep period but upkeep timer is running even if the main timer is stopped.
 *
 * Clock can by paused by {@link #pause(long)} method and resumed by {@link #start(long)}. Both
 * main and upkeep timers are paused.
 */
public class PlayerCountdown implements PausableClock, StoppableClock {

	/** Counter of elapsed reserve time. */
	private final CountdownClock reserveTime;
	/** Counter of elapsed upkeep time. */
	private final CountdownClock upkeepTime;
	/** How long did the reserve clock overlapped with upkeep clock. This time does not count
	 * towards the reserve time. */
	private LimitedTimer overlapTime;
	private boolean isRunning;
	/**
	 * Create new clock.
	 *
	 * @param baseTime How many milliseconds the player initially has.
	 * @param upkeepTime How many milliseconds each upkeep protection has.
	 */
	public PlayerCountdown(long baseTime, long upkeepTime) {
		this.reserveTime = new Countdown(baseTime);
		this.upkeepTime = new LimitedCountdown(upkeepTime);
		this.overlapTime = new LimitedTimer(0L);
	}

	@Override
	public void start(long when) {
		reserveTime.start(when);
		reserveTime.addTime(overlapTime.getTime(when));
		overlapTime = new LimitedTimer(upkeepTime.getRemainingTime(when));
		overlapTime.start(when);
		isRunning = true;
	}

	@Override
	public void stop(long when) {
		reserveTime.stop(when);
		overlapTime.stop(when);
		isRunning = false;
	}

	@Override
	public void restart(long when) {
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

	public void addTime(long amount) {
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
		if (isRunning) {
			reserveTime.addTime(overlapTime.getTime(when));
			overlapTime = new LimitedTimer(upkeepTime.getRemainingTime(when));
			overlapTime.start(when);
		}
	}

	public long getRemainingTime(long when) {
		long remainingReserve = reserveTime.getRemainingTime(when);
		long upkeepOverlap = overlapTime.getTime(when);
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
/*
	private long getElapsedReserveTime(long when) {
		long elapsedReserveTime = reserveTime.getTime(when);
		long elapsedUpkeepTime = upkeepTime.getTime(when);
		long result = 0L;
		if (elapsedUpkeepTime > remainingUpkeepTime) {
			result += elapsedUpkeepTime - remainingUpkeepTime;
		}
		if (elapsedReserveTime > elapsedUpkeepTime) {
			result += elapsedReserveTime - elapsedUpkeepTime;
		}
		return result;
	}

	private long getElapsedUpkeepTime(long when) {
		long elapsedUpkeepTime = upkeepTime.getTime(when);
		return Math.min(elapsedUpkeepTime, remainingUpkeepTime);
	}
	*/
}
