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

	/** Get current time in milliseconds. */
	public long getTimeInMilliseconds() {
		return time;
	}
}
