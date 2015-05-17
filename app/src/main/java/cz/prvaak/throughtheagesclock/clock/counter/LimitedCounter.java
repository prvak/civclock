package cz.prvaak.throughtheagesclock.clock.counter;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;

/**
 * Counter that cannot accumulate more than given amount of time.
 */
public class LimitedCounter extends CounterAdapter {

	/** How much time in milliseconds can this counter accumulate. */
	private final TimeAmount timeLimit;

	public LimitedCounter(TimeAmount timeLimit) {
		this(new Counter(), timeLimit);
	}

	public LimitedCounter(Counter target, TimeAmount timeLimit) {
		super(target);
		if (timeLimit.isNegative()) {
			throw new IllegalArgumentException("Time limit cannot be negative!");
		}
		this.timeLimit = timeLimit;
	}

	@Override
	public TimeAmount getElapsedTime(TimeInstant when) {
		TimeAmount realTime = super.getElapsedTime(when);
		return TimeAmount.min(realTime, timeLimit);
	}
}
