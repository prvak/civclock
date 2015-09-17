package cz.prvaak.civclock.clock.timer;

import cz.prvaak.civclock.TimeAmount;
import cz.prvaak.civclock.clock.counter.Counter;

/**
 * Timer implementation that uses {@link cz.prvaak.civclock.clock.counter.Counter}
 * in the background.
 */
public class Timer extends CounterToTimerAdapter {

	public Timer(TimeAmount baseTime) {
		super(new Counter(), baseTime);
	}
}
