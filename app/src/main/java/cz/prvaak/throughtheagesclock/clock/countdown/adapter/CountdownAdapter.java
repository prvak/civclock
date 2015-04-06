package cz.prvaak.throughtheagesclock.clock.countdown.adapter;

import cz.prvaak.throughtheagesclock.clock.countdown.CountdownClock;

/**
 * Adapter that delegates all calls to another countdown.
 */
public class CountdownAdapter implements CountdownClock {

	private final CountdownClock target;

	public CountdownAdapter(CountdownClock target) {
		this.target = target;
	}

	@Override
	public long getTime(long when) {
		return target.getTime(when);
	}

	@Override
	public long getRemainingTime(long when) {
		return target.getRemainingTime(when);
	}

	@Override
	public void addTime(long amount) {
		target.addTime(amount);
	}

	@Override
	public void stop(long when) {
		target.stop(when);
	}

	@Override
	public void start(long when) {
		target.start(when);
	}

	@Override
	public void restart(long when) {
		target.restart(when);
	}

	@Override
	public void pause(long when) {
		target.pause(when);
	}

	@Override
	public void resume(long when) {
		target.resume(when);
	}
}
