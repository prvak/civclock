package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.clock.PausableClock;

/**
 * Class for measuring elapsed time. Does not support stopping.
 */
public class Timer implements TimerClock {

	/** Time (in milliseconds) when the counter was started. */
	protected long initialTime;
	/** How much time elapsed before starting. It is updated when the counter is stopped. */
	protected long elapsedTime;
	/** Set to true when {@link #start(long)} method is called. */
	private boolean wasStarted;
	private boolean isPaused;
	private boolean isStopped;

	@Override
	public void start(long when) {
		initialTime = when;
		elapsedTime = 0;
		wasStarted = true;
	}

	@Override
	public long getTime(long when) {
		if (!wasStarted) {
			return 0;
		} else if (isStopped || isPaused) {
			return elapsedTime;
		} else {
			return when - initialTime + elapsedTime;
		}
	}

	@Override
	public void stop(long when) {
		checkForStarted();
		checkForTimeShift(when);
		if (isPaused) {
			throw new IllegalStateException("Cannot stop counter that is paused!");
		}
		if (isStopped) {
			throw new IllegalStateException("Cannot stop counter that is already stopped!");
		}

		isStopped = true;
		elapsedTime += when - initialTime;
		initialTime = when; // to detect time travel
	}

	@Override
	public void unstop(long when) {
		checkForStarted();
		checkForTimeShift(when);
		if (isPaused) {
			throw new IllegalStateException("Cannot unstop counter that is paused!");
		}
		if (!isStopped) {
			throw new IllegalStateException("Cannot unstop counter that is not stopped!");
		}

		isStopped = false;
		initialTime = when;
	}

	@Override
	public void pause(long when) {
		checkForTimeShift(when);
		if (isPaused) {
			throw new IllegalStateException("Cannot pause counter that is already paused!");
		}

		isPaused = true;
		elapsedTime += when - initialTime;
		initialTime = when; // to detect time travel
	}

	@Override
	public void unpause(long when) {
		checkForTimeShift(when);
		if (!isPaused) {
			throw new IllegalStateException("Cannot unpause counter that is not paused!");
		}

		isPaused = false;
		initialTime = when;
	}

	protected void checkForStarted() {
		if (!wasStarted) {
			throw new IllegalStateException("Clock have not been started yet!");
		}
	}

	protected void checkForTimeShift(long when) {
		if (initialTime > when) {
			throw new IllegalArgumentException("Cannot travel in time!");
		}
	}
}
