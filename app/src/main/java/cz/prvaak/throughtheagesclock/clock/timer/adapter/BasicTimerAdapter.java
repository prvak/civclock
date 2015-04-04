package cz.prvaak.throughtheagesclock.clock.timer.adapter;

import cz.prvaak.throughtheagesclock.clock.PausableClock;

/**
 * Adapter that delegates all calls to another clock.
 */
public class BasicTimerAdapter implements PausableClock {

	private final PausableClock target;

	public BasicTimerAdapter(PausableClock target) {
		this.target = target;
	}

	@Override
	public void pause(long when) {
		target.pause(when);
	}

	@Override
	public void unpause(long when) {
		target.unpause(when);
	}

	@Override
	public void start(long when) {
		target.start(when);
	}

	@Override
	public long getTime(long when) {
		return target.getTime(when);
	}
}
