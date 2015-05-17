package cz.prvaak.throughtheagesclock.clock;

import java.io.Serializable;

import cz.prvaak.throughtheagesclock.TimeInstant;

/**
 * Interface of basic clock.
 */
public interface Clock extends Serializable {

	/**
	 * Stop the clock.
	 *
	 * @param when Time in milliseconds when the clock was stopped.
	 */
	void stop(TimeInstant when);

	/**
	 * Resume the stopped clock.
	 *
	 * @param when Time in milliseconds when the clock was resumed.
	 */
	void start(TimeInstant when);

	/**
	 * Pause the clock.
	 *
	 * @param when Time in milliseconds when the clock was paused.
	 */
	void pause(TimeInstant when);

	/**
	 * Resume paused clock.
	 *
	 * @param when Time in milliseconds when the clock was resumed.
	 */
	void resume(TimeInstant when);
}
