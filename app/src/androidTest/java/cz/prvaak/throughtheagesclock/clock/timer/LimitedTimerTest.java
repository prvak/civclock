package cz.prvaak.throughtheagesclock.clock.timer;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.clock.timer.LimitedTimer} class.
 */
public class LimitedTimerTest extends InstrumentationTestCase {

	public void testGetRemainingTime() throws Exception {
		LimitedTimer timer = new LimitedTimer(10000L);
		Assert.assertEquals(0L, timer.getRemainingTime(0L));
		Assert.assertEquals(0L, timer.getRemainingTime(1000L));
		Assert.assertEquals(0L, timer.getRemainingTime(100000L));
		timer.start(0L);
		Assert.assertEquals(8000L, timer.getRemainingTime(2000L));
		Assert.assertEquals(5000L, timer.getRemainingTime(5000L));
		Assert.assertEquals(1L, timer.getRemainingTime(9999L));
		Assert.assertEquals(0L, timer.getRemainingTime(10000L));
		Assert.assertEquals(0L, timer.getRemainingTime(10001L));
		Assert.assertEquals(0L, timer.getRemainingTime(15000L));
		Assert.assertEquals(0L, timer.getRemainingTime(123000L));
	}

	public void testGetElapsedTime() throws Exception {
		LimitedTimer timer = new LimitedTimer(10000L);
		Assert.assertEquals(0L, timer.getElapsedTime(0L));
		Assert.assertEquals(0L, timer.getElapsedTime(1000L));
		Assert.assertEquals(0L, timer.getElapsedTime(100000L));
		timer.start(0L);
		Assert.assertEquals(2000L, timer.getElapsedTime(2000L));
		Assert.assertEquals(5000L, timer.getElapsedTime(5000L));
		Assert.assertEquals(9999L, timer.getElapsedTime(9999L));
		Assert.assertEquals(10000L, timer.getElapsedTime(10000L));
		Assert.assertEquals(10000L, timer.getElapsedTime(10001L));
		Assert.assertEquals(10000L, timer.getElapsedTime(15000L));
		Assert.assertEquals(10000L, timer.getElapsedTime(123000L));
	}

	public void testRestart() throws Exception {
		LimitedTimer timer = new LimitedTimer(10000L);
		timer.start(0L);
		Assert.assertEquals(0L, timer.getRemainingTime(20000L));
		timer.restart(30000L);
		Assert.assertEquals(9000L, timer.getRemainingTime(31000L));
		Assert.assertEquals(0L, timer.getRemainingTime(40000L));
	}

	public void testFirstStartEqualsRestart() throws Exception {
		LimitedTimer timer1 = new LimitedTimer(10000L);
		timer1.start(30000L);
		Assert.assertEquals(9000L, timer1.getRemainingTime(31000L));

		LimitedTimer timer2 = new LimitedTimer(10000L);
		timer2.restart(30000L);
		Assert.assertEquals(9000L, timer2.getRemainingTime(31000L));
	}

	public void testGetElapsedTimeAfterRestartWithNewTimeLimit() throws Exception {
		LimitedTimer timer = new LimitedTimer(10000L);
		timer.start(0L);
		Assert.assertEquals(10000L, timer.getElapsedTime(20000L));
		timer.restart(40000L, 5000L);
		Assert.assertEquals(5000L, timer.getElapsedTime(60000L));
	}

	public void testNegativeTimeLimitNotAllowed() throws Exception {
		try {
			new LimitedTimer(-5000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testNegativeTimeLimitAfterRestartNotAllowed() throws Exception {
		LimitedTimer timer = new LimitedTimer(5000L);
		try {
			timer.restart(0L, -5000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
