package cz.prvaak.throughtheagesclock.clock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link ElapsedTimeClock} class.
 */
public class ElapsedTimeClockTest extends InstrumentationTestCase {

	public void testGetTimeOfStopped() throws Exception {
		ElapsedTimeClock elapsedTime = new ElapsedTimeClock();
		try {
			elapsedTime.getTime(1000L);
			Assert.fail("Should have thrown IllegalStateException.");
		} catch (IllegalStateException e) {
			// success
		}
	}

	public void testGetTimeOfStarted() throws Exception {
		ElapsedTimeClock elapsedTime = new ElapsedTimeClock();
		elapsedTime.start(1000L);
		assertEquals(1000L, elapsedTime.getTime(2000L));
	}

	public void testStop() throws Exception {
		ElapsedTimeClock elapsedTime = new ElapsedTimeClock();
		elapsedTime.start(1000L);
		elapsedTime.stop(3000L);
		assertEquals(2000L, elapsedTime.getTime(3000L));
		assertEquals(2000L, elapsedTime.getTime(4000L));
	}

	public void testUnstop() throws Exception {
		ElapsedTimeClock elapsedTime = new ElapsedTimeClock();
		elapsedTime.start(1000L);
		elapsedTime.stop(3000L);
		elapsedTime.unstop(4000L);
		assertEquals(3000L, elapsedTime.getTime(5000L));
	}

	public void testRestart() throws Exception {
		ElapsedTimeClock elapsedTime = new ElapsedTimeClock();
		elapsedTime.start(1000L);
		assertEquals(2000L, elapsedTime.getTime(3000L));
		elapsedTime.start(4000L);
		assertEquals(1000L, elapsedTime.getTime(5000L));
	}

	public void testPause() throws Exception {
		ElapsedTimeClock elapsedTime = new ElapsedTimeClock();
		elapsedTime.start(1000L);
		elapsedTime.pause(2000L);
		assertEquals(1000L, elapsedTime.getTime(2000L));
		assertEquals(1000L, elapsedTime.getTime(3000L));
	}

	public void testUnpause() throws Exception {
		ElapsedTimeClock elapsedTime = new ElapsedTimeClock();
		elapsedTime.start(1000L);
		elapsedTime.pause(2000L);
		elapsedTime.unpause(3000L);
		assertEquals(2000L, elapsedTime.getTime(4000L));
	}

	public void testStopOfStoppedCounter() throws Exception {
		ElapsedTimeClock elapsedTime = new ElapsedTimeClock();
		try {
			elapsedTime.stop(1000L);
			Assert.fail("Should have thrown IllegalStateException.");
		} catch (IllegalStateException e) {
			// success
		}
	}

	public void testStopWithEarlierTime() throws Exception {
		ElapsedTimeClock elapsedTime = new ElapsedTimeClock();
		elapsedTime.start(2000L);
		try {
			elapsedTime.stop(1000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
