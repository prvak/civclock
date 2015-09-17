package cz.prvaak.civclock.clock.counter;

import cz.prvaak.civclock.TimeAmount;
import cz.prvaak.civclock.TimeInstant;

/**
 * Adapter that delegates all calls to another timer.
 */
public class CounterAdapter implements CounterClock {

	private final CounterClock target;

	CounterAdapter(CounterClock target) {
		this.target = target;
	}

	@Override
	public void restart(TimeInstant when) {
		target.restart(when);
	}

	@Override
	public TimeAmount getElapsedTime(TimeInstant when) {
		return target.getElapsedTime(when);
	}

	@Override
	public void stop(TimeInstant when) {
		target.stop(when);
	}

	@Override
	public void start(TimeInstant when) {
		target.start(when);
	}

	@Override
	public void pause(TimeInstant when) {
		target.pause(when);
	}

	@Override
	public void resume(TimeInstant when) {
		target.resume(when);
	}

	@Override
	public String toString() {
		return target.toString();
	}
}
