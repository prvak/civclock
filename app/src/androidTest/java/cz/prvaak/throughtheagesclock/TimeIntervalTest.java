package cz.prvaak.throughtheagesclock;

import android.test.InstrumentationTestCase;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.TimeInterval} class.
 */
public class TimeIntervalTest extends InstrumentationTestCase {

	public void testGetHours() throws Exception {
		TimeInterval interval = new TimeInterval(5 * 60 * 60 * 1000L);
		assertEquals(5L, interval.getHours());
		assertEquals(0L, interval.getMinutes());
		assertEquals(0L, interval.getSeconds());
		assertEquals(0L, interval.getMilliseconds());
	}

	public void testGetMinutes() throws Exception {
		TimeInterval interval = new TimeInterval(35 * 60 * 1000L);
		assertEquals(0L, interval.getHours());
		assertEquals(35L, interval.getMinutes());
		assertEquals(0L, interval.getSeconds());
		assertEquals(0L, interval.getMilliseconds());
	}

	public void testGetSeconds() throws Exception {
		TimeInterval interval = new TimeInterval(15 * 1000L);
		assertEquals(0L, interval.getHours());
		assertEquals(0L, interval.getMinutes());
		assertEquals(15L, interval.getSeconds());
		assertEquals(0L, interval.getMilliseconds());
	}

	public void testGetMilliseconds() throws Exception {
		TimeInterval interval = new TimeInterval(521L);
		assertEquals(0L, interval.getHours());
		assertEquals(0L, interval.getMinutes());
		assertEquals(0L, interval.getSeconds());
		assertEquals(521L, interval.getMilliseconds());
	}

	public void testGetAll() throws Exception {
		TimeInterval interval = new TimeInterval(
				3 * 60 * 60 * 1000L
				+ 12 * 60 * 1000L
				+ 58 * 1000L
				+ 135L);
		assertEquals(3L, interval.getHours());
		assertEquals(12L, interval.getMinutes());
		assertEquals(58L, interval.getSeconds());
		assertEquals(135L, interval.getMilliseconds());
	}
}
