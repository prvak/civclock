package cz.prvaak.throughtheagesclock;

import java.util.concurrent.TimeUnit;

/**
 * Representation of a time between to instants.
 */
public class TimeAmount {

	public final static TimeAmount EMPTY = new TimeAmount(0L);

	private final long totalTimeMs;
	private final long hours;
	private final long minutes;
	private final long seconds;
	private final long milliseconds;

	public TimeAmount(long totalTimeMs) {
		this.totalTimeMs = totalTimeMs;
		long remainingMs = totalTimeMs < 0 ? -totalTimeMs : totalTimeMs;

		this.hours = TimeUnit.MILLISECONDS.toHours(remainingMs);
		remainingMs -= TimeUnit.HOURS.toMillis(this.hours);

		this.minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMs);
		remainingMs -= TimeUnit.MINUTES.toMillis(this.minutes);

		this.seconds = TimeUnit.MILLISECONDS.toSeconds(remainingMs);
		remainingMs -= TimeUnit.SECONDS.toMillis(this.seconds);

		this.milliseconds = remainingMs;
	}

	public long getHours() {
		return this.hours;
	}

	public long getMinutes() {
		return this.minutes;
	}

	public long getSeconds() {
		return this.seconds;
	}

	public long getMilliseconds() {
		return this.milliseconds;
	}

	public boolean isNegative() {
		return totalTimeMs < 0;
	}

	public TimeAmount add(TimeAmount amount) {
		return new TimeAmount(totalTimeMs + amount.totalTimeMs);
	}

	public TimeAmount subtract(TimeAmount amount) {
		return new TimeAmount(totalTimeMs - amount.totalTimeMs);
	}

	public static TimeAmount min(TimeAmount interval1, TimeAmount interval2) {
		return interval1.totalTimeMs < interval2.totalTimeMs ? interval1 : interval2;
	}

	public static TimeAmount max(TimeAmount interval1, TimeAmount interval2) {
		return interval1.totalTimeMs > interval2.totalTimeMs ? interval1 : interval2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TimeAmount that = (TimeAmount) o;

		return totalTimeMs == that.totalTimeMs;
	}

	@Override
	public int hashCode() {
		return (int) (totalTimeMs ^ (totalTimeMs >>> 32 ));
	}

	@Override
	public String toString() {
		return String.format("%d", totalTimeMs);
	}
}
