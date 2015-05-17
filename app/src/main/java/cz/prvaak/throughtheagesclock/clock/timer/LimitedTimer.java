package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;

/**
 * Timer that starts at given value and never goes below zero.
 */
public class LimitedTimer extends TimerAdapter {

	private boolean wasStarted;
	private TimeAmount timeLimit;

	public LimitedTimer(TimeAmount timeLimit) {
		super(new Timer(timeLimit));
		this.timeLimit = timeLimit;
	}

	@Override
	public void start(TimeInstant when) {
		super.start(when);
		wasStarted = true;
	}

	@Override
	public void restart(TimeInstant when) {
		super.restart(when);
		wasStarted = true;
	}

	@Override
	public void restart(TimeInstant when, TimeAmount newBaseTime) {
		super.restart(when, newBaseTime);
		timeLimit = newBaseTime;
		wasStarted = true;
	}

	@Override
	public TimeAmount getElapsedTime(TimeInstant when) {
		TimeAmount realTime = super.getElapsedTime(when);
		return TimeAmount.min(realTime, timeLimit);
	}

	@Override
	public TimeAmount getRemainingTime(TimeInstant when) {
		if (wasStarted) {
			return TimeAmount.max(TimeAmount.EMPTY, super.getRemainingTime(when));
		} else {
			return TimeAmount.EMPTY;
		}
	}
}
