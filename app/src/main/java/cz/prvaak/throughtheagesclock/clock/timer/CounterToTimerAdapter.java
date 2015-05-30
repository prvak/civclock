package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.counter.CounterClock;

/**
 * Adapter that changes given counter go timer.
 */
public class CounterToTimerAdapter implements TimerClock {
	private final CounterClock counter;
	private TimeAmount baseTime;

	public CounterToTimerAdapter(CounterClock counter, TimeAmount baseTime) {
		this.counter = counter;
		if (baseTime.isNegative()) {
			throw new IllegalArgumentException("Base time cannot be negative!");
		}
		this.baseTime = baseTime;
	}

	@Override
	public TimeAmount getElapsedTime(TimeInstant when) {
		return counter.getElapsedTime(when);
	}

	@Override
	public TimeAmount getRemainingTime(TimeInstant when) {
		return baseTime.subtract(counter.getElapsedTime(when));
	}

	@Override
	public void addTime(TimeInstant when, TimeAmount amount) {
		baseTime = baseTime.add(amount);
	}

	@Override
	public void restart(TimeInstant when) {
		counter.restart(when);
	}

	@Override
	public void restart(TimeInstant when, TimeAmount newBaseTime) {
		if (newBaseTime.isNegative()) {
			throw new IllegalArgumentException("Base time cannot be negative!");
		}

		counter.restart(when);
		baseTime = newBaseTime;
	}

	@Override
	public void stop(TimeInstant when) {
		counter.stop(when);
	}

	@Override
	public void start(TimeInstant when) {
		counter.start(when);
	}

	@Override
	public void pause(TimeInstant when) {
		counter.pause(when);
	}

	@Override
	public void resume(TimeInstant when) {
		counter.resume(when);
	}
}
