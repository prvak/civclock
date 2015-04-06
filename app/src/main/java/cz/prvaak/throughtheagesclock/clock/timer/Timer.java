package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.clock.counter.Counter;

/**
 * Countdown implementation that uses {@link cz.prvaak.throughtheagesclock.clock.counter.Counter}
 * in the background.
 */
public class Timer extends CounterToTimerAdapter implements TimerClock {

	public Timer(long baseTime) {
		super(new Counter(), baseTime);
	}
}
