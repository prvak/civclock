package cz.prvaak.throughtheagesclock.clock;

/**
 * Class for measuring elapsed time.
 */
public class ElapsedTimeClock implements PausableClock, StoppableClock {

	/** Time (in milliseconds) when the counter was started. */
	private long initialTime;
	/** How much time elapsed before starting. It is updated when the counter is stopped. */
	private long elapsedTime;
	/** Set to true when {@link #start(long)} method is called. */
	private boolean wasStarted;
	private boolean isStopped;
	private boolean isPaused;

	@Override
	public void start(long when) {
		initialTime = when;
		elapsedTime = 0;
		wasStarted = true;
	}

	@Override
	public long getTime(long when) {
		checkForStarted();

		if (isStopped || isPaused) {
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
		checkForStarted();
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
		checkForStarted();
		checkForTimeShift(when);
		if (!isPaused) {
			throw new IllegalStateException("Cannot unpause counter that is not paused!");
		}

		isPaused = false;
		initialTime = when;
	}

	private void checkForStarted() {
		if (!wasStarted) {
			throw new IllegalStateException("Clock have not been started yet!");
		}
	}

	private void checkForTimeShift(long when) {
		if (initialTime > when) {
			throw new IllegalArgumentException("Cannot travel in time!");
		}
	}
}
