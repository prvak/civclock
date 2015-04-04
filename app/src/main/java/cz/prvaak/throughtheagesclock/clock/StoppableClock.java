package cz.prvaak.throughtheagesclock.clock;

/**
 * Interface for clocks that can be stopped.
 */
public interface StoppableClock extends Clock {

	/**
	 * Stop the clock.
	 *
	 * @param when Time in milliseconds when the clock was stopped.
	 */
	void stop(long when);

	/**
	 * Resume the stopped clock.
	 *
	 * @param when Time in milliseconds when the clock was resumed.
	 */
	void unstop(long when);

}
