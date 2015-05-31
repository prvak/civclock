package cz.prvaak.throughtheagesclock.clock.counter;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;

/**
 * Class for measuring elapsed time.
 */
public class Counter implements CounterClock {

	/** Time (in milliseconds) when the counter was started. */
	private TimeInstant initialTime;
	/** How much time elapsed before starting. It is updated when the counter is stopped. */
	private TimeAmount elapsedTime = TimeAmount.EMPTY;
	/** Set to true when {@link #initialTime} is set. */
	private boolean isInitialized;
	/** True when the clock is paused and to false when the clock is resumed. */
	private boolean isPaused;
	/**
	 * Set to true when the clock is started or restarted and to false when the clock is stopped.
	 */
	private boolean isStopped = true;

	@Override
	public TimeAmount getElapsedTime(TimeInstant when) {
		if (!isInitialized) {
			return TimeAmount.EMPTY;
		} else if (isStopped || isPaused) {
			return elapsedTime;
		} else {
			return initialTime.getDistance(when).add(elapsedTime);
		}
	}

	@Override
	public void start(TimeInstant when) {
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
	public void stop(TimeInstant when) {
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
	public void pause(TimeInstant when) {
		checkForTimeShift(when);
		if (isPaused) {
			throw new IllegalStateException("Cannot pause counter that is already paused!");
		}

		updateElapsedTime(when);
		setInitialTime(when);
		isPaused = true;
	}

	@Override
	public void resume(TimeInstant when) {
		checkForTimeShift(when);
		if (!isPaused) {
			throw new IllegalStateException("Cannot resume counter that is not paused!");
		}

		setInitialTime(when);
		isPaused = false;
	}

	@Override
	public void restart(TimeInstant when) {
		isStopped = false;
		stop(when);
		elapsedTime = TimeAmount.EMPTY;
		start(when);
	}

	private void updateElapsedTime(TimeInstant when) {
		if (isInitialized && !isStopped) {
			elapsedTime = elapsedTime.add(initialTime.getDistance(when));
		}
	}

	private void setInitialTime(TimeInstant when) {
		initialTime = when;
		isInitialized = true;
	}

	private void checkForTimeShift(TimeInstant when) {
		if (isInitialized && initialTime.isInFutureFrom(when)) {
			throw new IllegalArgumentException("Cannot travel in time!");
		}
	}
}
