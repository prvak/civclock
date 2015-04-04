package cz.prvaak.throughtheagesclock.clock.timer.adapter;

import cz.prvaak.throughtheagesclock.clock.timer.Timer;

/**
 * Timer that cannot accumulate more than given amount of time.
 */
public class LimitedTimer extends TimerAdapter {

	private final long timeLimit;

	public LimitedTimer(Timer target, long timeLimit) {
		super(target);
		this.timeLimit = timeLimit;
	}

	@Override
	public long getTime(long when) {
		long realTime = super.getTime(when);
		return Math.min(realTime, timeLimit);
	}
}
