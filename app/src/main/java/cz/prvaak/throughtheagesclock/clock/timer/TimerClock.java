package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.clock.counter.CounterClock;

/**
 * Interface for clocks that count remaining time.
 */
public interface TimerClock extends CounterClock {

	/**
	 * Restart the timer with new time limit.
	 *
	 * @param when Time in milliseconds when the timer was restarted.
	 * @param newBaseTime How much time in milliseconds is remaining.
	 */
	void restart(long when, long newBaseTime);

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
