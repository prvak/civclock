package cz.prvaak.throughtheagesclock;

import java.util.concurrent.TimeUnit;

/**
 * Representation of a time between to instants.
 */
public class TimeInterval {
	private final long totalTimeMs;
	private final long hours;
	private final long minutes;
	private final long seconds;
	private final long milliseconds;

	public TimeInterval(long totalTimeMs) {
		this.totalTimeMs = totalTimeMs;
		long remainingMs = totalTimeMs;

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
}
