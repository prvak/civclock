package cz.prvaak.throughtheagesclock.clock.counter;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;

/**
 * Tests of {@link Counter} class.
 */
public class CounterTest extends InstrumentationTestCase {

	public void testGetTimeOfStopped() throws Exception {
		Counter elapsedTime = new Counter();
		assertEquals(new TimeAmount(0L), elapsedTime.getElapsedTime(new TimeInstant(2000L)));
	}

	public void testGetTimeOfStarted() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(1000L));
		assertEquals(new TimeAmount(1000L), elapsedTime.getElapsedTime(new TimeInstant(2000L)));
	}

	public void testRestart() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(1000L));
		assertEquals(new TimeAmount(2000L), elapsedTime.getElapsedTime(new TimeInstant(3000L)));
		elapsedTime.restart(new TimeInstant(4000L));
		assertEquals(new TimeAmount(1000L), elapsedTime.getElapsedTime(new TimeInstant(5000L)));
	}

	public void testStop() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(1000L));
		elapsedTime.stop(new TimeInstant(3000L));
		assertEquals(new TimeAmount(2000L), elapsedTime.getElapsedTime(new TimeInstant(3000L)));
		assertEquals(new TimeAmount(2000L), elapsedTime.getElapsedTime(new TimeInstant(4000L)));
	}

	public void testUnstop() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(1000L));
		elapsedTime.stop(new TimeInstant(3000L));
		elapsedTime.start(new TimeInstant(4000L));
		assertEquals(new TimeAmount(3000L), elapsedTime.getElapsedTime(new TimeInstant(5000L)));
	}

	public void testStopOfStoppedCounter() throws Exception {
		Counter elapsedTime = new Counter();
		try {
			elapsedTime.stop(new TimeInstant(2000L));
			Assert.fail("Should have thrown IllegalStateException.");
		} catch (IllegalStateException e) {
			// success
		}
	}

	public void testStopWithEarlierTime() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(2000L));
		try {
			elapsedTime.stop(new TimeInstant(1000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testPause() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(1000L));
		elapsedTime.pause(new TimeInstant(2000L));
		assertEquals(new TimeAmount(1000L), elapsedTime.getElapsedTime(new TimeInstant(2000L)));
		assertEquals(new TimeAmount(1000L), elapsedTime.getElapsedTime(new TimeInstant(3000L)));
	}

	public void testResume() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(1000L));
		elapsedTime.pause(new TimeInstant(2000L));
		elapsedTime.resume(new TimeInstant(3000L));
		assertEquals(new TimeAmount(2000L), elapsedTime.getElapsedTime(new TimeInstant(4000L)));
	}

	public void testRepeatedResume() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(1000L));
		elapsedTime.pause(new TimeInstant(2000L));
		elapsedTime.resume(new TimeInstant(3000L));
		elapsedTime.pause(new TimeInstant(4000L));
		elapsedTime.resume(new TimeInstant(5000L));
		assertEquals(new TimeAmount(3000L), elapsedTime.getElapsedTime(new TimeInstant(6000L)));
	}

	public void testRepeatedResumeOfStopped() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.pause(new TimeInstant(1000L));
		elapsedTime.resume(new TimeInstant(2000L));
		elapsedTime.pause(new TimeInstant(3000L));
		elapsedTime.resume(new TimeInstant(4000L));
		assertEquals(new TimeAmount(0L), elapsedTime.getElapsedTime(new TimeInstant(5000L)));
	}

	public void testPauseWithEarlierTime() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(2000L));
		try {
			elapsedTime.pause(new TimeInstant(1000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testRestartWithEarlierTime() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(2000L));
		try {
			elapsedTime.restart(new TimeInstant(1000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testResumeWithEarlierTime() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(2000L));
		elapsedTime.pause(new TimeInstant(3000L));
		try {
			elapsedTime.resume(new TimeInstant(1000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testStartWithNegativeTime() throws Exception {
		Counter elapsedTime = new Counter();
		elapsedTime.start(new TimeInstant(-2000L));
		assertEquals(new TimeAmount(2000L), elapsedTime.getElapsedTime(new TimeInstant(0L)));
	}
}
