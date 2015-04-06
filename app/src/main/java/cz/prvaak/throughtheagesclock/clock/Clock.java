package cz.prvaak.throughtheagesclock.clock;

/**
 * Interface of basic clock.
 */
public interface Clock {

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
	void start(long when);

	/**
	 * Start the clock.
	 *
	 * @param when Time in milliseconds when the clock was started.
	 */
	void restart(long when);

	/**
	 * Pause the clock.
	 *
	 * @param when Time in milliseconds when the clock was paused.
	 */
	void pause(long when);

	/**
	 * Resume paused clock.
	 *
	 * @param when Time in milliseconds when the clock was resumed.
	 */
	void resume(long when);
}
