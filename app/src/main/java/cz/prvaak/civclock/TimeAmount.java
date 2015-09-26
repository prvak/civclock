package cz.prvaak.civclock;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Representation of a time between to instants.
 */
public class TimeAmount implements Serializable {

	public final static TimeAmount EMPTY = new TimeAmount(0L);

	public enum Formatting {
		SIMPLE, PRECISE
	}

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

	public TimeAmount(long hours, long minutes, long seconds, long milliseconds) {
		this(TimeUnit.HOURS.toMillis(hours)
				+ TimeUnit.MINUTES.toMillis(minutes)
				+ TimeUnit.SECONDS.toMillis(seconds)
				+ milliseconds);
	}

	public TimeAmount(String text) {
		this(textToMilliseconds(text));
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

	public String format(Formatting formatting) {
		StringBuilder text = new StringBuilder();
		if (isNegative()) {
			text.append("-");
		}
		if (getHours() > 0) {
			// eg. 2:07:31
			text.append(String.format("%d:%02d:%02d", getHours(), getMinutes(), getSeconds()));
		} else if (getMinutes() > 0) {
			// eg. 07:31
			text.append(String.format("%d:%02d", getMinutes(), getSeconds()));
		} else {
			switch (formatting) {
				case SIMPLE:
					// eg. 0:31
					text.append(String.format("%d", getSeconds()));
					break;
				case PRECISE:
					// eg. 31.6
					text.append(String.format("%02d.%01d", getSeconds(), getMilliseconds() / 100));
					break;
			}
		}
		return text.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TimeAmount that = (TimeAmount) o;
		return totalTimeMs == that.totalTimeMs;
	}

	@Override
	public int hashCode() {
		return (int) (totalTimeMs ^ (totalTimeMs >>> 32));
	}

	@Override
	public String toString() {
		return String.format("%d", totalTimeMs);
	}

	private static long textToMilliseconds(final String originalText) {
		String text = originalText;
		if (text.isEmpty()) {
			return 0L;
		}

		String[] times = new String[] {"0", "0", "0"};

		long sign = 1;
		if (text.startsWith("-")) {
			text = text.substring(1, text.length());
			sign = -1;
		}

		if (text.contains(":")) {
			// Split the text by colons.
			String[] parts = text.split(":");
			if (parts.length > 3) {
				throw new IllegalArgumentException(
						String.format("Text %s contains too many colons!", originalText));
			}
			for (int i = 0; i < parts.length; i++) {
				String part = parts[i];
				if ((part.length() > 2 && i != 0) || (part.length() == 0)) {
					throw new IllegalArgumentException(
							String.format("Text %s is in a wrong format!", originalText));
				}
				times[parts.length - i - 1] = part;
			}
		} else {
			// Split the text to two-digit parts.
			int unit = 0;
			while (text.length() > 0 && unit < times.length) {
				if (text.length() >= 2 && unit <= 2) {
					times[unit] = text.substring(text.length() - 2, text.length());
					text = text.substring(0, text.length() - 2);
				} else {
					times[unit] = text;
					text = "";
				}
				unit++;
			}
		}


		try {
			long seconds = Long.valueOf(times[0]);
			long minutes = Long.valueOf(times[1]);
			long hours = Long.valueOf(times[2]);

			if (seconds >= 60 || minutes >= 60) {
				throw new IllegalArgumentException(
						String.format("Text %s does not contain a valid time!", originalText));
			}

			return sign * (TimeUnit.SECONDS.toMillis(seconds)
					+ TimeUnit.MINUTES.toMillis(minutes)
					+ TimeUnit.HOURS.toMillis(hours));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					String.format("Text %s does not contain a valid time!", originalText), e);
		}
	}
}
