package cz.prvaak.throughtheagesclock.clock.timer;

import android.test.InstrumentationTestCase;

/**
 * Tests of {@link Timer} class.
 */
public class TimerTest extends InstrumentationTestCase {

	public void testGetRemainingTime() throws Exception {
		Timer remainingTime = new Timer(10000L);
		remainingTime.restart(0L);
		assertEquals(9000L, remainingTime.getRemainingTime(1000L));
		assertEquals(0L, remainingTime.getRemainingTime(10000L));
		assertEquals(-1000L, remainingTime.getRemainingTime(11000L));
	}
}
