package cz.prvaak.throughtheagesclock;

/**
 * Abstraction around current time getter.
 *
 * Useful for testing classes dependent on current time.
 */
public class TimeInstant {

	private final long time;

	public TimeInstant() {
		this.time = System.currentTimeMillis();
	}

	/** This constructor should be used only in tests.*/
	public TimeInstant(long when) {
		this.time = when;
	}

	/** Get current time in milliseconds. */
	public long getTimeInMilliseconds() {
		return time;
	}

	public TimeAmount getDistance(TimeInstant when) {
		return new TimeAmount(when.time - time);
	}

	public boolean isInFutureFrom(TimeInstant when) {
		return time > when.time;
	}
}
