package cz.prvaak.throughtheagesclock.clock.countdown;

import cz.prvaak.throughtheagesclock.clock.UniversalClock;
import cz.prvaak.throughtheagesclock.clock.timer.TimerClock;

/**
 * Interface for clocks that count remaining time.
 */
public interface CountdownClock extends UniversalClock, TimerClock {

	/**
	 * Get remaining time.
	 *
	 * @param when Time in milliseconds when the question was asked.
	 * @return Remaining time in milliseconds. Can be negative.
	 */
	long getRemainingTime(long when);

	/**
	 * Add given amount of time to remaining time.
	 *
	 * @param amount How many milliseconds to add.
	 */
	void addTime(long amount);
}
