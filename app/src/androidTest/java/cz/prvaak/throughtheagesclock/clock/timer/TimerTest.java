package cz.prvaak.throughtheagesclock.clock.timer;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link Timer} class.
 */
public class TimerTest extends InstrumentationTestCase {

	public void testGetTimeOfStopped() throws Exception {
		Timer elapsedTime = new Timer();
		assertEquals(0L, elapsedTime.getTime(2000L));
	}

	public void testGetTimeOfStarted() throws Exception {
		Timer elapsedTime = new Timer();
		elapsedTime.restart(1000L);
		assertEquals(1000L, elapsedTime.getTime(2000L));
	}

	public void testRestart() throws Exception {
		Timer elapsedTime = new Timer();
		elapsedTime.restart(1000L);
		assertEquals(2000L, elapsedTime.getTime(3000L));
		elapsedTime.restart(4000L);
		assertEquals(1000L, elapsedTime.getTime(5000L));
	}

	public void testStop() throws Exception {
		Timer elapsedTime = new Timer();
		elapsedTime.restart(1000L);
		elapsedTime.stop(3000L);
		assertEquals(2000L, elapsedTime.getTime(3000L));
		assertEquals(2000L, elapsedTime.getTime(4000L));
	}

	public void testUnstop() throws Exception {
		Timer elapsedTime = new Timer();
		elapsedTime.restart(1000L);
		elapsedTime.stop(3000L);
		elapsedTime.start(4000L);
		assertEquals(3000L, elapsedTime.getTime(5000L));
	}

	public void testStopOfStoppedCounter() throws Exception {
		Timer elapsedTime = new Timer();
		try {
			elapsedTime.stop(2000L);
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

	public void testPause() throws Exception {
		Timer elapsedTime = new Timer();
		elapsedTime.start(1000L);
		elapsedTime.pause(2000L);
		assertEquals(1000L, elapsedTime.getTime(2000L));
		assertEquals(1000L, elapsedTime.getTime(3000L));
	}

	public void testUnpause() throws Exception {
		Timer elapsedTime = new Timer();
		elapsedTime.restart(1000L);
		elapsedTime.pause(2000L);
		elapsedTime.resume(3000L);
		assertEquals(2000L, elapsedTime.getTime(4000L));
	}

}
