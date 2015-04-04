package cz.prvaak.throughtheagesclock.clock.timer;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.clock.timer.BasicTimer} class.
 */
public class BasicTimerTest extends InstrumentationTestCase {

	public void testGetTimeOfStopped() throws Exception {
		BasicTimer elapsedTime = new BasicTimer();
		try {
			elapsedTime.getTime(1000L);
			Assert.fail("Should have thrown IllegalStateException.");
		} catch (IllegalStateException e) {
			// success
		}
	}

	public void testGetTimeOfStarted() throws Exception {
		BasicTimer elapsedTime = new BasicTimer();
		elapsedTime.start(1000L);
		assertEquals(1000L, elapsedTime.getTime(2000L));
	}

	public void testRestart() throws Exception {
		BasicTimer elapsedTime = new BasicTimer();
		elapsedTime.start(1000L);
		assertEquals(2000L, elapsedTime.getTime(3000L));
		elapsedTime.start(4000L);
		assertEquals(1000L, elapsedTime.getTime(5000L));
	}

	public void testPause() throws Exception {
		BasicTimer elapsedTime = new BasicTimer();
		elapsedTime.start(1000L);
		elapsedTime.pause(2000L);
		assertEquals(1000L, elapsedTime.getTime(2000L));
		assertEquals(1000L, elapsedTime.getTime(3000L));
	}

	public void testUnpause() throws Exception {
		BasicTimer elapsedTime = new BasicTimer();
		elapsedTime.start(1000L);
		elapsedTime.pause(2000L);
		elapsedTime.unpause(3000L);
		assertEquals(2000L, elapsedTime.getTime(4000L));
	}

}
