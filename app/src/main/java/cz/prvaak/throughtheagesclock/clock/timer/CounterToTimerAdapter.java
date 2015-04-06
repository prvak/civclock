package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.clock.counter.CounterClock;

/**
 *
 */
public class CounterToTimerAdapter implements TimerClock {
	private final CounterClock counter;
	private long baseTime;

	public CounterToTimerAdapter(CounterClock counter, long baseTime) {
		this.counter = counter;
		if (baseTime < 0) {
			throw new IllegalArgumentException("Base time cannot be negative!");
		}
		this.baseTime = baseTime;
	}

	@Override
	public long getElapsedTime(long when) {
		return counter.getElapsedTime(when);
	}

	@Override
	public long getRemainingTime(long when) {
		return baseTime - counter.getElapsedTime(when);
	}

	@Override
	public void addTime(long amount) {
		baseTime += amount;
	}

	@Override
	public void restart(long when) {
		counter.restart(when);
	}

	@Override
	public void restart(long when, long newBaseTime) {
		counter.restart(when);
		if (newBaseTime < 0) {
			throw new IllegalArgumentException("Base time cannot be negative!");
		}
		baseTime = newBaseTime;
	}

	@Override
	public void stop(long when) {
		counter.stop(when);
	}

	@Override
	public void start(long when) {
		counter.start(when);
	}

	@Override
	public void pause(long when) {
		counter.pause(when);
	}

	@Override
	public void resume(long when) {
		counter.resume(when);
	}

	@Override
	public String toString() {
		return String.format("%s Base: %d", counter.toString(), baseTime);
	}
}
