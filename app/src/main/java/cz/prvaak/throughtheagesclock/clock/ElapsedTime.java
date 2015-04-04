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
	private boolean isStarted;
	private boolean isPaused;

	/**
	 * Start the counter.
	 *
	 * @param when Time in milliseconds when the counter was started.
	 */
	public void start(long when) {
		if (isStarted) {
			throw new IllegalArgumentException("Cannot start counter that is already started!");
		}

		startedTime = when;
		isStarted = true;
	}

	/**
	 * Stop the counter.
	 *
	 * @param when Time in milliseconds when the counter was stopped.
	 * @return Total elapsed time.
	 */
	public long stop(long when) {
		if (!isStarted) {
			throw new IllegalArgumentException("Cannot stop counter that is not running!");
		}
		if (when < startedTime) {
			throw new IllegalArgumentException("Cannot stop counter before it was started!");
		}

		elapsedTime += when - startedTime;
		startedTime = 0L;
		isStarted = false;
		return elapsedTime;
	}

	/** Stops the counter and discards elapsed time. */
	public void reset() {
		isStarted = false;
		startedTime = 0L;
		elapsedTime = 0L;
	}

	public void pause(long when) {
		if (isPaused) {
			return;
		}

		isPaused = true;
		if (isStarted) {
			elapsedTime += when - startedTime;
			startedTime = 0L;
		}
	}

	public void resume(long when) {
		if (!isPaused) {
			return;
		}

		if (isStarted) {
			startedTime = when;
		}
		isPaused = false;
	}

	/**
	 * Get total elapsed time.
	 *
	 * @param when Time (in milliseconds) when the question was asked.
	 * @return Total elapsed time since the counter was started for the first time.
	 *	Zero if the counter is stopped.
	 */
	public long getElapsedTime(long when) {
		if (!isStarted || isPaused) {
			return elapsedTime;
		}
		if (when < startedTime) {
			throw new IllegalArgumentException("Time is in the past!");
		}

		return when - startedTime + elapsedTime;
	}

	public boolean isStarted() {
		return isStarted;
	}
}
