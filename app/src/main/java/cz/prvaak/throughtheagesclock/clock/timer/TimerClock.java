package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.clock.UniversalClock;
import cz.prvaak.throughtheagesclock.clock.counter.CounterClock;

/**
 * Interface for clocks that count remaining time.
 */
public interface TimerClock extends UniversalClock, CounterClock {

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
