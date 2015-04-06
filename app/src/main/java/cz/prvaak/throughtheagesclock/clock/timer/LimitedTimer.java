package cz.prvaak.throughtheagesclock.clock.timer;

/**
 * Timer that starts at given value and never goes below zero.
 */
public class LimitedTimer extends TimerAdapter {

	private boolean wasStarted;
	private long timeLimit;

	public LimitedTimer(long timeLimit) {
		super(new Timer(timeLimit));
		this.timeLimit = timeLimit;
	}

	@Override
	public void start(long when) {
		super.start(when);
		wasStarted = true;
	}

	@Override
	public void restart(long when) {
		super.restart(when);
		wasStarted = true;
	}

	@Override
	public void restart(long when, long newBaseTime) {
		super.restart(when, newBaseTime);
		timeLimit = newBaseTime;
		wasStarted = true;
	}

	@Override
	public long getElapsedTime(long when) {
		long realTime = super.getElapsedTime(when);
		return Math.min(realTime, timeLimit);
	}

	@Override
	public long getRemainingTime(long when) {
		if (wasStarted) {
			return Math.max(0L, super.getRemainingTime(when));
		} else {
			return 0L;
		}
	}
}
