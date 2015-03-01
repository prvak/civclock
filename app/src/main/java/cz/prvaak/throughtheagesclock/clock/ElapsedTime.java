package cz.prvaak.throughtheagesclock.clock;

/**
 * Class for measuring elapsed time.
 */
public class ElapsedTime {

	/** Time (in milliseconds) when the counter was started. */
	private long startedTime;
	/** How much time elapsed before starting. It is updated when the counter is stopped. */
	private long elapsedTime;
	/** Whether the counter is running or not. */
	private boolean isRunning;

	/**
	 * Start the counter.
	 *
	 * @param when Time in milliseconds when the counter was started.
	 */
	public void start(long when) {
		if (isRunning) {
			throw new IllegalArgumentException("Cannot start counter that is already started!");
		}

		startedTime = when;
		isRunning = true;
	}

	/**
	 * Stop the counter.
	 *
	 * @param when Time in milliseconds when the counter was stopped.
	 * @return Total elapsed time.
	 */
	public long stop(long when) {
		if (!isRunning) {
			throw new IllegalArgumentException("Cannot stop counter that is not running!");
		}
		if (when < startedTime) {
			throw new IllegalArgumentException("Cannot stop counter before it was started!");
		}

		elapsedTime += when - startedTime;
		startedTime = 0L;
		isRunning = false;
		return elapsedTime;
	}

	/** Stops the counter and discards elapsed time. */
	public void reset() {
		isRunning = false;
		startedTime = 0L;
		elapsedTime = 0L;
	}

	/**
	 * Get total elapsed time.
	 *
	 * @param when Time (in milliseconds) when the question was asked.
	 * @return Total elapsed time since the counter was started for the first time.
	 *	Zero if the counter is stopped.
	 */
	public long getElapsedTime(long when) {
		if (!isRunning) {
			return elapsedTime;
		}
		if (when < startedTime) {
			throw new IllegalArgumentException("Time is in the past!");
		}

		return when - startedTime + elapsedTime;
	}

	public boolean isRunning() {
		return isRunning;
	}
}
