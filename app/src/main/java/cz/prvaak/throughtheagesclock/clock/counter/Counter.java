package cz.prvaak.throughtheagesclock.clock.counter;

/**
 * Class for measuring elapsed time.
 */
public class Counter implements CounterClock {

	/** Time (in milliseconds) when the counter was started. */
	private long initialTime;
	/** How much time elapsed before starting. It is updated when the counter is stopped. */
	private long elapsedTime;
	/** Set to true when {@link #initialTime} is set. */
	private boolean isInitialized;
	/** True when the clock is paused and to false when the clock is resumed. */
	private boolean isPaused;
	/** Set to true when the clock is started or restarted and to false when the clock is stopped. */
	private boolean isStopped = true;

	@Override
	public long getElapsedTime(long when) {
		if (!isInitialized) {
			return 0;
		} else if (isStopped || isPaused) {
			return elapsedTime;
		} else {
			return when - initialTime + elapsedTime;
		}
	}

	@Override
	public void start(long when) {
		checkForTimeShift(when);
		if (isPaused) {
			throw new IllegalStateException("Cannot start counter that is paused!");
		}
		if (!isStopped) {
			throw new IllegalStateException("Cannot start counter that is not stopped!");
		}

		setInitialTime(when);
		isStopped = false;
	}

	@Override
	public void stop(long when) {
		checkForTimeShift(when);
		if (isPaused) {
			throw new IllegalStateException("Cannot stop counter that is paused!");
		}
		if (isStopped) {
			throw new IllegalStateException("Cannot stop counter that is already stopped!");
		}

		updateElapsedTime(when);
		setInitialTime(when);
		isStopped = true;
	}

	@Override
	public void pause(long when) {
		checkForTimeShift(when);
		if (isPaused) {
			throw new IllegalStateException("Cannot pause counter that is already paused!");
		}

		updateElapsedTime(when);
		setInitialTime(when);
		isPaused = true;
	}

	@Override
	public void resume(long when) {
		checkForTimeShift(when);
		if (!isPaused) {
			throw new IllegalStateException("Cannot resume counter that is not paused!");
		}

		setInitialTime(when);
		isPaused = false;
	}

	@Override
	public void restart(long when) {
		isStopped = false;
		stop(when);
		elapsedTime = 0;
		start(when);
	}

	@Override
	public String toString() {
		return String.format("Initial: %d", initialTime);
	}

	private void updateElapsedTime(long when) {
		if (isInitialized && !isStopped) {
			elapsedTime += when - initialTime;
		}
	}

	private void setInitialTime(long when) {
		initialTime = when;
		isInitialized = true;
	}

	private void checkForTimeShift(long when) {
		if (isInitialized && initialTime > when) {
			throw new IllegalArgumentException("Cannot travel in time!");
		}
	}
}
