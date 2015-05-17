package cz.prvaak.throughtheagesclock;

import android.test.InstrumentationTestCase;

/**
 * Test of {@link TimeInstant} class.
 */
public class TimeInstantTest extends InstrumentationTestCase {

	public void testNow() throws Exception {
		long before = System.currentTimeMillis();
		TimeInstant timeInstant = new TimeInstant();
		long now = timeInstant.getTimeInMilliseconds();
		long after = System.currentTimeMillis();
		assertTrue(before <= now);
		assertTrue(now <= after);
	}
}
