package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.clock.PausableClock;

/**
 * Timer that cannot accumulate more than given amount of time.
 */
public class LimitedBasicTimer extends BasicTimerAdapter {

	private final long timeLimit;

	public LimitedBasicTimer(PausableClock target, long timeLimit) {
		super(target);
		this.timeLimit = timeLimit;
	}

	@Override
	public long getTime(long when) {
		long realTime = super.getTime(when);
		return Math.max(realTime, timeLimit);
	}
}
