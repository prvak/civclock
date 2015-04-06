package cz.prvaak.throughtheagesclock.clock.counter;

/**
 * Counter that cannot accumulate more than given amount of time.
 */
public class LimitedCounter extends CounterAdapter {

	/** How much time in milliseconds can this counter accumulate. */
	private final long timeLimit;

	public LimitedCounter(long timeLimit) {
		this(new Counter(), timeLimit);
	}

	public LimitedCounter(Counter target, long timeLimit) {
		super(target);
		if (timeLimit < 0) {
			throw new IllegalArgumentException("Time limit cannot be negative!");
		}
		this.timeLimit = timeLimit;
	}

	@Override
	public long getElapsedTime(long when) {
		long realTime = super.getElapsedTime(when);
		return Math.min(realTime, timeLimit);
	}
}
