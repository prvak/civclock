package cz.prvaak.throughtheagesclock.clock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import cz.prvaak.throughtheagesclock.clock.timer.Timer;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.clock.timer.Timer} class.
 */
public class TimerClockTest extends InstrumentationTestCase {

	public void testStop() throws Exception {
		Timer elapsedTime = new Timer();
		elapsedTime.start(1000L);
		elapsedTime.stop(3000L);
		assertEquals(2000L, elapsedTime.getTime(3000L));
		assertEquals(2000L, elapsedTime.getTime(4000L));
	}

	public void testUnstop() throws Exception {
		Timer elapsedTime = new Timer();
		elapsedTime.start(1000L);
		elapsedTime.stop(3000L);
		elapsedTime.unstop(4000L);
		assertEquals(3000L, elapsedTime.getTime(5000L));
	}

	public void testStopOfStoppedCounter() throws Exception {
		Timer elapsedTime = new Timer();
		try {
			elapsedTime.stop(1000L);
			Assert.fail("Should have thrown IllegalStateException.");
		} catch (IllegalStateException e) {
			// success
		}
	}

	public void testStopWithEarlierTime() throws Exception {
		Timer elapsedTime = new Timer();
		elapsedTime.start(2000L);
		try {
			elapsedTime.stop(1000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
