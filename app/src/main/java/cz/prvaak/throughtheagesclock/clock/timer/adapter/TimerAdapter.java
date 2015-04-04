package cz.prvaak.throughtheagesclock.clock.timer.adapter;

import cz.prvaak.throughtheagesclock.clock.UniversalClock;

/**
 * Adapter that delegates all calls to another clock.
 */
public class TimerAdapter extends BasicTimerAdapter {

	private final UniversalClock target;

	public TimerAdapter(UniversalClock target) {
		super(target);
		this.target = target;
	}

	public void stop(long when) {
		target.stop(when);
	}

	public void unstop(long when) {
		target.unstop(when);
	}
}
