package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.clock.counter.CounterClock;

/**
 *
 */
public class CounterToTimerAdapter implements TimerClock {
	private final CounterClock timer;
	private long baseTime;

	public CounterToTimerAdapter(CounterClock timer, long baseTime) {
		this.timer = timer;
		this.baseTime = baseTime;
	}

	@Override
	public long getTime(long when) {
		return timer.getTime(when);
	}

	@Override
	public long getRemainingTime(long when) {
		return baseTime - timer.getTime(when);
	}

	@Override
	public void addTime(long amount) {
		baseTime += amount;
	}

	@Override
	public void stop(long when) {
		timer.stop(when);
	}

	@Override
	public void start(long when) {
		timer.start(when);
	}

	@Override
	public void restart(long when) {
		timer.restart(when);
	}

	@Override
	public void pause(long when) {
		timer.pause(when);
	}

	@Override
	public void resume(long when) {
		timer.resume(when);
	}
}
