package cz.prvaak.throughtheagesclock.clock;

/**
 * Interface of basic clock.
 */
public interface Clock {

	/**
	 * Start the clock.
	 *
	 * @param when Time in milliseconds when the clock was started.
	 */
	void start(long when);

}
