package cz.prvaak.civclock.clock.timer;

import cz.prvaak.civclock.TimeAmount;
import cz.prvaak.civclock.TimeInstant;

/**
 * Adapter that delegates all calls to another timer.
 */
public class TimerAdapter implements TimerClock {

	private final TimerClock target;

	public TimerAdapter(TimerClock target) {
		this.target = target;
	}

	@Override
	public TimeAmount getElapsedTime(TimeInstant when) {
		return target.getElapsedTime(when);
	}

	@Override
	public TimeAmount getRemainingTime(TimeInstant when) {
		return target.getRemainingTime(when);
	}

	@Override
	public void addTime(TimeInstant when, TimeAmount amount) {
		target.addTime(when, amount);
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
	public void restart(TimeInstant when) {
		target.restart(when);
	}

	@Override
	public void restart(TimeInstant when, TimeAmount newBaseTime) {
		target.restart(when, newBaseTime);
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
