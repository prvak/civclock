package cz.prvaak.throughtheagesclock.clock.countdown.adapter;

import cz.prvaak.throughtheagesclock.clock.timer.Timer;
import cz.prvaak.throughtheagesclock.clock.timer.adapter.LimitedTimer;

/**
 * Countdown that starts at given value and never goes below zero.
 */
public class LimitedCountdown extends CountdownAdapter {

	public LimitedCountdown(long timeLimit) {
		super(new TimerToCountdownAdapter(new LimitedTimer(new Timer(), timeLimit), timeLimit));
	}
}
