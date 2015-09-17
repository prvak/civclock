package cz.prvaak.civclock;

import android.test.InstrumentationTestCase;

/**
 * Test of {@link TimeInstant} class.
 */
public class TimeInstantTest extends InstrumentationTestCase {

	public void testGetTimeInMilliseconds() throws Exception {
		long before = System.currentTimeMillis();
		TimeInstant timeInstant = new TimeInstant();
		long now = timeInstant.getTimeInMilliseconds();
		long after = System.currentTimeMillis();
		assertTrue(before <= now);
		assertTrue(now <= after);
	}

	public void testGetDistance() throws Exception {
		TimeInstant timeInstant1 = new TimeInstant(10000L);
		TimeInstant timeInstant2 = new TimeInstant(20000L);
		TimeAmount distance = timeInstant1.getDistance(timeInstant2);
		assertEquals(new TimeAmount(10000L), distance);
	}

	public void testIsInFutureFrom() throws Exception {
		TimeInstant past = new TimeInstant(15000L);
		TimeInstant now = new TimeInstant(20000L);
		TimeInstant future = new TimeInstant(25000L);
		assertTrue(now.isInFutureFrom(past));
		assertFalse(now.isInFutureFrom(future));
		assertFalse(now.isInFutureFrom(now));
	}
}
