package cz.prvaak.throughtheagesclock.clock.counter;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link Counter} class.
 */
public class CounterTest extends InstrumentationTestCase {

	public void testGetTimeOfStopped() throws Exception {
		Counter elapsedTime = new Counter();
		assertEquals(0L, elapsedTime.getTime(2000L));
	}

	public void testGetTimeOfStarted() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.restart(1000L);
		assertEquals(1000L, elapsedTime.getTime(2000L));
	}

	public void testRestart() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.restart(1000L);
		assertEquals(2000L, elapsedTime.getTime(3000L));
		elapsedTime.restart(4000L);
		assertEquals(1000L, elapsedTime.getTime(5000L));
	}

	public void testStop() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.restart(1000L);
		elapsedTime.stop(3000L);
		assertEquals(2000L, elapsedTime.getTime(3000L));
		assertEquals(2000L, elapsedTime.getTime(4000L));
	}

	public void testUnstop() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.restart(1000L);
		elapsedTime.stop(3000L);
		elapsedTime.start(4000L);
		assertEquals(3000L, elapsedTime.getTime(5000L));
	}

	public void testStopOfStoppedCounter() throws Exception {
		Counter elapsedTime = new Counter();
		try {
			elapsedTime.stop(2000L);
			Assert.fail("Should have thrown IllegalStateException.");
		} catch (IllegalStateException e) {
			// success
		}
	}

	public void testStopWithEarlierTime() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(2000L);
		try {
			elapsedTime.stop(1000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testPause() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(1000L);
		elapsedTime.pause(2000L);
		assertEquals(1000L, elapsedTime.getTime(2000L));
		assertEquals(1000L, elapsedTime.getTime(3000L));
	}

	public void testUnpause() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.restart(1000L);
		elapsedTime.pause(2000L);
		elapsedTime.resume(3000L);
		assertEquals(2000L, elapsedTime.getTime(4000L));
	}

}
