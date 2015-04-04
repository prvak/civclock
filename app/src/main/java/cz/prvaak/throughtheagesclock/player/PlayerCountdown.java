package cz.prvaak.throughtheagesclock.player;

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
 * Clock can by paused by {@link #pause(long)} method and resumed by {@link #unstop(long)}. Both
 * main and upkeep timers are paused.
 */
public class PlayerCountdown implements CountdownClock {

	private final TimerClock totalTime;
	/** Counter of elapsed reserve time. */
	private final CountdownClock reserveTime;
	/** Counter of elapsed upkeep time. */
	private final CountdownClock upkeepTime;

	/**
	 * Create new clock.
	 *
	 * @param baseTime How many milliseconds the player initially has.
	 * @param upkeepTime How many milliseconds each upkeep protection has.
	 */
	public PlayerCountdown(long baseTime, long upkeepTime) {
		this.totalTime = new Timer();
		this.reserveTime = new Countdown(baseTime);
		this.upkeepTime = new LimitedCountdown(upkeepTime);
	}

	@Override
	public void start(long when) {
		totalTime.start(when);
		reserveTime.start(when);
	}

	@Override
	public void stop(long when) {
		totalTime.stop(when);
		reserveTime.stop(when);
	}

	@Override
	public void unstop(long when) {
		totalTime.start(when);
		reserveTime.unstop(when);
	}

	@Override
	public void pause(long when) {
		reserveTime.pause(when);
		upkeepTime.pause(when);
	}

	@Override
	public void unpause(long when) {
		reserveTime.unpause(when);
		upkeepTime.unpause(when);
	}

	@Override
	public long getTime(long when) {
		return reserveTime.getTime(when);
	}

	@Override
	public long getRemainingTime(long when) {
		long remainingReserve = reserveTime.getRemainingTime(when);
		long elapsedUpkeep = upkeepTime.getTime(when);
		return remainingReserve + elapsedUpkeep;
	}

	@Override
	public void addTime(long amount) {
		reserveTime.addTime(amount);
	}

	/**
	 * Start the upkeep protection period.
	 * The protection period timer cannot be stopped, only paused. Calling this method again
	 * restarts the timer with initial value.
	 *
	 * @param when Current time in milliseconds.
	 */
	public void upkeep(long when) {
		upkeepTime.start(when);
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
