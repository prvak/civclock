package cz.prvaak.throughtheagesclock.clock.countdown;

import android.test.InstrumentationTestCase;

import cz.prvaak.throughtheagesclock.clock.timer.Timer;

/**
 * Tests of {@link Countdown} class.
 */
public class CountdownTest extends InstrumentationTestCase {

	public void testGetRemainingTime() throws Exception {
		Countdown remainingTime = new Countdown(10000L);
		remainingTime.start(0L);
		assertEquals(9000L, remainingTime.getRemainingTime(1000L));
		assertEquals(0L, remainingTime.getRemainingTime(10000L));
		assertEquals(-1000L, remainingTime.getRemainingTime(11000L));
	}
}
