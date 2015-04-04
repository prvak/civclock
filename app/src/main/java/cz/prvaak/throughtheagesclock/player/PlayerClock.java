package cz.prvaak.throughtheagesclock.player;

import cz.prvaak.throughtheagesclock.clock.countdown.CountdownClock;
import cz.prvaak.throughtheagesclock.clock.UniversalClock;
import cz.prvaak.throughtheagesclock.clock.timer.Timer;
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
public class PlayerClock implements CountdownClock {

	/** Counter of elapsed reserve time. */
	private final Timer reserveTime = new Timer();
	/** Counter of elapsed upkeep time. */
	private final LimitedTimer upkeepTime;
	/** How many milliseconds was remaining before {@link #reserveTime} was started. */
	private long remainingReserveTime;
	/**
	 * How many milliseconds of upkeep protection period was remaining before {@link #upkeepTime}
	 * was started.
	 */
	private long remainingUpkeepTime;

	/**
	 * Create new clock.
	 *
	 * @param initialTime How many milliseconds the player initially has.
	 * @param defaultUpkeepTime How many milliseconds each upkeep protection has.
	 */
	public PlayerClock(long initialTime, long defaultUpkeepTime) {
		if (defaultUpkeepTime < 0) {
			throw new IllegalArgumentException("Upkeep time cannot be negative!");
		}

		this.remainingReserveTime = initialTime;
		this.upkeepTime = new LimitedTimer(new Timer(), defaultUpkeepTime);
	}

	@Override
	public void start(long when) {
		reserveTime.start(when);
	}

	@Override
	public void stop(long when) {
		remainingReserveTime -= getElapsedReserveTime(when);
		remainingUpkeepTime -= getElapsedUpkeepTime(when);
		reserveTime.stop(when);
		/*
		if (upkeepTime.isStarted()) {
			upkeepTime.reset();
			upkeepTime.start(when);
		}
		*/
	}


	@Override
	public void unstop(long when) {
	}

	@Override
	public void pause(long when) {
		upkeepTime.pause(when);
		reserveTime.pause(when);
	}

	@Override
	public void unpause(long when) {
		upkeepTime.unpause(when);
		reserveTime.unpause(when);
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
	 * Increase remaining time.
	 *
	 * @param amount How many milliseconds to add to remaining time. Use negative value to substract
	 *	time.
	 */
	public void add(long amount) {
		remainingReserveTime += amount;
	}

	/**
	 * How many milliseconds are actually remaining.
	 *
	 * @param when Current time in milliseconds.
	 * @return Remaining time in milliseconds.
	 */
	public long getRemainingTime(long when) {
		return remainingReserveTime - getElapsedReserveTime(when);
	}

	@Override
	public void addTime(long amount) {

	}

	/**
	 * How many milliseconds of upkeep protection period are remaining.
	 *
	 * @param when Current time in milliseconds.
	 * @return Remaining upkeep protection in milliseconds.
	 */
	public long getRemainingUpkeepTime(long when) {
		return remainingUpkeepTime - getElapsedUpkeepTime(when);
	}

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
}
