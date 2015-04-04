package cz.prvaak.throughtheagesclock.clock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link ProtectedTimerClock} class.
 */
public class ProtectedTimerClockTest extends InstrumentationTestCase {

	public void testGetTimeOfStopped() throws Exception {
		ProtectedTimerClock elapsedTime = new ProtectedTimerClock();
		try {
			elapsedTime.getTime(1000L);
			Assert.fail("Should have thrown IllegalStateException.");
		} catch (IllegalStateException e) {
			// success
		}
	}

	public void testGetTimeOfStarted() throws Exception {
		ProtectedTimerClock elapsedTime = new ProtectedTimerClock();
		elapsedTime.start(1000L);
		assertEquals(1000L, elapsedTime.getTime(2000L));
	}

	public void testRestart() throws Exception {
		ProtectedTimerClock elapsedTime = new ProtectedTimerClock();
		elapsedTime.start(1000L);
		assertEquals(2000L, elapsedTime.getTime(3000L));
		elapsedTime.start(4000L);
		assertEquals(1000L, elapsedTime.getTime(5000L));
	}

	public void testPause() throws Exception {
		ProtectedTimerClock elapsedTime = new ProtectedTimerClock();
		elapsedTime.start(1000L);
		elapsedTime.pause(2000L);
		assertEquals(1000L, elapsedTime.getTime(2000L));
		assertEquals(1000L, elapsedTime.getTime(3000L));
	}

	public void testUnpause() throws Exception {
		ProtectedTimerClock elapsedTime = new ProtectedTimerClock();
		elapsedTime.start(1000L);
		elapsedTime.pause(2000L);
		elapsedTime.unpause(3000L);
		assertEquals(2000L, elapsedTime.getTime(4000L));
	}

}
