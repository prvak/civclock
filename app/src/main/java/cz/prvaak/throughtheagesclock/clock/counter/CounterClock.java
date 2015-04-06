package cz.prvaak.throughtheagesclock.clock.counter;

import cz.prvaak.throughtheagesclock.clock.UniversalClock;

/**
 * Interface for clocks that count elapsed time.
 */
public interface CounterClock extends UniversalClock {

	/**
	 * Get total elapsed time.
	 *
	 * @param when Time in milliseconds when the question was asked.
	 * @return Total elapsed time since the clock was started.
	 */
	long getTime(long when);
}
