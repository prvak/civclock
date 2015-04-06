package cz.prvaak.throughtheagesclock.clock.countdown.adapter;

import cz.prvaak.throughtheagesclock.clock.timer.Timer;
import cz.prvaak.throughtheagesclock.clock.timer.adapter.LimitedTimer;

/**
 * Countdown that starts at given value and never goes below zero.
 */
public class LimitedCountdown extends CountdownAdapter {

	boolean wasStarted;

	public LimitedCountdown(long timeLimit) {
		super(new TimerToCountdownAdapter(new LimitedTimer(new Timer(), timeLimit), timeLimit));
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
