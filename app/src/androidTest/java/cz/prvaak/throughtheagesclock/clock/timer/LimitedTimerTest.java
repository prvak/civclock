package cz.prvaak.throughtheagesclock.clock.timer;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.clock.timer.LimitedTimer} class.
 */
public class LimitedTimerTest extends InstrumentationTestCase {

	private static LimitedTimer createTimer() {
		return new LimitedTimer(new TimeAmount(10000L));
	}

	public void testGetRemainingTime() throws Exception {
		LimitedTimer timer = createTimer();
		Assert.assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(0L)));
		Assert.assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(1000L)));
		Assert.assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(100000L)));
		timer.start(new TimeInstant(0L));
		Assert.assertEquals(new TimeAmount(8000L), timer.getRemainingTime(new TimeInstant(2000L)));
		Assert.assertEquals(new TimeAmount(5000L), timer.getRemainingTime(new TimeInstant(5000L)));
		Assert.assertEquals(new TimeAmount(1L), timer.getRemainingTime(new TimeInstant(9999L)));
		Assert.assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(10000L)));
		Assert.assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(10001L)));
		Assert.assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(15000L)));
		Assert.assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(123000L)));
	}

	public void testGetElapsedTime() throws Exception {
		LimitedTimer timer = createTimer();
		Assert.assertEquals(new TimeAmount(0L), timer.getElapsedTime(new TimeInstant(0L)));
		Assert.assertEquals(new TimeAmount(0L), timer.getElapsedTime(new TimeInstant(1000L)));
		Assert.assertEquals(new TimeAmount(0L), timer.getElapsedTime(new TimeInstant(100000L)));
		timer.start(new TimeInstant(0L));
		Assert.assertEquals(new TimeAmount(2000L), timer.getElapsedTime(new TimeInstant(2000L)));
		Assert.assertEquals(new TimeAmount(5000L), timer.getElapsedTime(new TimeInstant(5000L)));
		Assert.assertEquals(new TimeAmount(9999L), timer.getElapsedTime(new TimeInstant(9999L)));
		Assert.assertEquals(new TimeAmount(10000L), timer.getElapsedTime(new TimeInstant(10000L)));
		Assert.assertEquals(new TimeAmount(10000L), timer.getElapsedTime(new TimeInstant(10001L)));
		Assert.assertEquals(new TimeAmount(10000L), timer.getElapsedTime(new TimeInstant(15000L)));
		Assert.assertEquals(new TimeAmount(10000L), timer.getElapsedTime(new TimeInstant(123000L)));
	}

	public void testRestart() throws Exception {
		LimitedTimer timer = createTimer();
		timer.start(new TimeInstant(0L));
		Assert.assertEquals(new TimeAmount(0L), timer.getRemainingTime(
				new TimeInstant(20000L)));
		timer.restart(new TimeInstant(30000L));
		Assert.assertEquals(new TimeAmount(9000L), timer.getRemainingTime(
				new TimeInstant(31000L)));
		Assert.assertEquals(new TimeAmount(0L), timer.getRemainingTime(
				new TimeInstant(40000L)));
	}

	public void testFirstStartEqualsRestart() throws Exception {
		LimitedTimer timer1 = createTimer();
		timer1.start(new TimeInstant(30000L));
		Assert.assertEquals(new TimeAmount(9000L), timer1.getRemainingTime(
				new TimeInstant(31000L)));

		LimitedTimer timer2 = createTimer();
		timer2.restart(new TimeInstant(30000L));
		Assert.assertEquals(new TimeAmount(9000L), timer2.getRemainingTime(
				new TimeInstant(31000L)));
	}

	public void testGetElapsedTimeAfterRestartWithNewTimeLimit() throws Exception {
		LimitedTimer timer = createTimer();
		timer.start(new TimeInstant(0L));
		Assert.assertEquals(new TimeAmount(10000L), timer.getElapsedTime(
				new TimeInstant(20000L)));
		timer.restart(new TimeInstant(40000L), new TimeAmount(5000L));
		Assert.assertEquals(new TimeAmount(5000L), timer.getElapsedTime(
				new TimeInstant(60000L)));
	}

	public void testNegativeTimeLimitNotAllowed() throws Exception {
		try {
			new LimitedTimer(new TimeAmount(-5000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testNegativeTimeLimitAfterRestartNotAllowed() throws Exception {
		LimitedTimer timer = createTimer();
		try {
			timer.restart(new TimeInstant(0L), new TimeAmount(-5000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
