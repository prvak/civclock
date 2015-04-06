package cz.prvaak.throughtheagesclock.clock.counter;

/**
 * Timer that cannot accumulate more than given amount of time.
 */
public class LimitedCounter extends CounterAdapter {

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
	public long getTime(long when) {
		long realTime = super.getTime(when);
		return Math.min(realTime, timeLimit);
	}
}
