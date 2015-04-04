package cz.prvaak.throughtheagesclock.clock.timer.adapter;

import cz.prvaak.throughtheagesclock.clock.StoppableClock;
import cz.prvaak.throughtheagesclock.clock.UniversalClock;
import cz.prvaak.throughtheagesclock.clock.timer.Timer;
import cz.prvaak.throughtheagesclock.clock.timer.TimerClock;

/**
 * Adapter that delegates all calls to another timer.
 */
public class TimerAdapter implements TimerClock {

	private final Timer target;

	public TimerAdapter(Timer target) {
		this.target = target;
	}

	@Override
	public void start(long when) {
		target.start(when);
	}

	@Override
	public long getTime(long when) {
		return target.getTime(when);
	}

	@Override
	public void stop(long when) {
		target.stop(when);
	}

	@Override
	public void unstop(long when) {
		target.unstop(when);
	}

	@Override
	public void pause(long when) {
		target.pause(when);
	}

	@Override
	public void unpause(long when) {
		target.unpause(when);
	}
}