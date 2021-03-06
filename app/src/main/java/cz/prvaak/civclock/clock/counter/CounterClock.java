package cz.prvaak.civclock.clock.counter;

import cz.prvaak.civclock.TimeAmount;
import cz.prvaak.civclock.TimeInstant;
import cz.prvaak.civclock.clock.Clock;

/**
 * Interface for clocks that count elapsed time.
 */
public interface CounterClock extends Clock {

	/**
	 * Get total elapsed time.
	 *
	 * @param when Time in milliseconds when the question was asked.
	 * @return Total elapsed time since the clock was started.
	 */
	TimeAmount getElapsedTime(TimeInstant when);

	/**
	 * Restart the counter.
	 *
	 * @param when Time in milliseconds when the clock was restarted.
	 */
	void restart(TimeInstant when);
}
