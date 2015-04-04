package cz.prvaak.throughtheagesclock.clock.countdown;

import cz.prvaak.throughtheagesclock.clock.UniversalClock;
import cz.prvaak.throughtheagesclock.clock.countdown.adapter.TimerToCountdownAdapter;
import cz.prvaak.throughtheagesclock.clock.timer.Timer;

/**
 * Countdown implementation that uses {@link cz.prvaak.throughtheagesclock.clock.timer.Timer}
 * in the background.
 */
public class Countdown extends TimerToCountdownAdapter implements CountdownClock {

	public Countdown(long baseTime) {
		super(new Timer(), baseTime);
	}
}
