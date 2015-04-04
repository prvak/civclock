package cz.prvaak.throughtheagesclock.clock;

/**
 * Interface for clocks that can be paused and resumed.
 */
public interface PausableClock extends Clock {

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
	void unpause(long when);
}
