package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.clock.Clock;
import cz.prvaak.throughtheagesclock.clock.PausableClock;
import cz.prvaak.throughtheagesclock.clock.StoppableClock;
import cz.prvaak.throughtheagesclock.clock.UniversalClock;

/**
 * Interface for clocks that count elapsed time.
 */
public interface TimerClock extends UniversalClock {

	/**
	 * Get total elapsed time.
	 *
	 * @param when Time in milliseconds when the question was asked.
	 * @return Total elapsed time since the clock was started.
	 */
	long getTime(long when);
}
