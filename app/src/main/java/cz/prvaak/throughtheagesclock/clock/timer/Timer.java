package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.clock.counter.Counter;

/**
 * Timer implementation that uses {@link cz.prvaak.throughtheagesclock.clock.counter.Counter}
 * in the background.
 */
public class Timer extends CounterToTimerAdapter implements TimerClock {

	public Timer(TimeAmount baseTime) {
		super(new Counter(), baseTime);
	}
}
