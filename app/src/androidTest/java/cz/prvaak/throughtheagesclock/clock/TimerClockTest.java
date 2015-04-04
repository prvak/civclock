package cz.prvaak.throughtheagesclock.clock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link TimerClock} class.
 */
public class TimerClockTest extends InstrumentationTestCase {

	public void testStop() throws Exception {
		TimerClock elapsedTime = new TimerClock();
		elapsedTime.start(1000L);
		elapsedTime.stop(3000L);
		assertEquals(2000L, elapsedTime.getTime(3000L));
		assertEquals(2000L, elapsedTime.getTime(4000L));
	}

	public void testUnstop() throws Exception {
		TimerClock elapsedTime = new TimerClock();
		elapsedTime.start(1000L);
		elapsedTime.stop(3000L);
		elapsedTime.unstop(4000L);
		assertEquals(3000L, elapsedTime.getTime(5000L));
	}

	public void testStopOfStoppedCounter() throws Exception {
		TimerClock elapsedTime = new TimerClock();
		try {
			elapsedTime.stop(1000L);
			Assert.fail("Should have thrown IllegalStateException.");
		} catch (IllegalStateException e) {
			// success
		}
	}

	public void testStopWithEarlierTime() throws Exception {
		TimerClock elapsedTime = new TimerClock();
		elapsedTime.start(2000L);
		try {
			elapsedTime.stop(1000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
