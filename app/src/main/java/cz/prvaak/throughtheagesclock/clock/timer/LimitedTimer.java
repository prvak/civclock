package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.clock.counter.Counter;
import cz.prvaak.throughtheagesclock.clock.counter.LimitedCounter;

/**
 * Countdown that starts at given value and never goes below zero.
 */
public class LimitedTimer extends TimerAdapter {

	boolean wasStarted;

	public LimitedTimer(long timeLimit) {
		super(new CounterToTimerAdapter(new LimitedCounter(new Counter(), timeLimit), timeLimit));
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
	public long getRemainingTime(long when) {
		if (wasStarted) {
			return super.getRemainingTime(when);
		} else {
			return 0;
		}
	}
}
