package cz.prvaak.throughtheagesclock.clock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link ElapsedTime} class.
 */
public class ElapsedTimeTest extends InstrumentationTestCase {

	public void testGetElapsedTimeOfStopped() throws Exception {
		ElapsedTime elapsedTime = new ElapsedTime();
		assertEquals(0L, elapsedTime.getElapsedTime(1000L));
	}

	public void testGetRemainingTimeOfStarted() throws Exception {
		ElapsedTime elapsedTime = new ElapsedTime();
		elapsedTime.start(1000L);
		assertEquals(1000L, elapsedTime.getElapsedTime(2000L));
	}

	public void testStartStop() throws Exception {
		ElapsedTime elapsedTime = new ElapsedTime();
		elapsedTime.start(1000L);
		assertEquals(1000L, elapsedTime.getElapsedTime(2000L));
		assertEquals(2000L, elapsedTime.stop(3000L));
		assertEquals(2000L, elapsedTime.getElapsedTime(4000L));
	}

	public void testStartPauseResumeStop() throws Exception {
		ElapsedTime elapsedTime = new ElapsedTime();
		elapsedTime.start(1000L);
		elapsedTime.pause(2000L);
		elapsedTime.resume(3000L);
		assertEquals(2000L, elapsedTime.stop(4000L));
	}

	public void testRepeatedStartStop() throws Exception {
		ElapsedTime elapsedTime = new ElapsedTime();
		elapsedTime.start(0L);
		assertEquals(1000L, elapsedTime.stop(1000L));
		elapsedTime.start(2000L);
		assertEquals(2000L, elapsedTime.stop(3000L));
		assertEquals(2000L, elapsedTime.getElapsedTime(4000L));
	}

	public void testResetStopsCounter() throws Exception {
		ElapsedTime elapsedTime = new ElapsedTime();
		elapsedTime.start(0L);
		assertEquals(1000L, elapsedTime.getElapsedTime(1000L));
		elapsedTime.reset();
		assertEquals(0L, elapsedTime.getElapsedTime(2000L));
	}

	public void testStartOfStartedCounter() throws Exception {
		ElapsedTime elapsedTime = new ElapsedTime();
		elapsedTime.start(1000L);
		try {
			elapsedTime.start(2000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testStopOfStoppedCounter() throws Exception {
		ElapsedTime elapsedTime = new ElapsedTime();
		try {
			elapsedTime.stop(1000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testStopWithEarlierTime() throws Exception {
		ElapsedTime elapsedTime = new ElapsedTime();
		elapsedTime.start(2000L);
		try {
			elapsedTime.stop(1000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
