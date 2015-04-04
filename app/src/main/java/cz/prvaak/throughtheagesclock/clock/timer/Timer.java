package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.clock.StoppableClock;

/**
 * Created by michal on 4/4/15.
 */
public class Timer extends BasicTimer implements StoppableClock {

	private boolean isStopped;

	@Override
	public long getTime(long when) {
		checkForStarted();

		if (isStopped() || isPaused()) {
			return elapsedTime;
		} else {
			return when - initialTime + elapsedTime;
		}
	}

	@Override
	public void stop(long when) {
		checkForStarted();
		checkForTimeShift(when);
		if (isPaused()) {
			throw new IllegalStateException("Cannot stop counter that is paused!");
		}
		if (isStopped()) {
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
		if (isPaused()) {
			throw new IllegalStateException("Cannot unstop counter that is paused!");
		}
		if (!isStopped()) {
			throw new IllegalStateException("Cannot unstop counter that is not stopped!");
		}

		isStopped = false;
		initialTime = when;
	}

	protected boolean isStopped() {
		return isStopped;
	}
}
