package cz.prvaak.throughtheagesclock.clock.countdown.adapter;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link LimitedCountdown} class.
 */
public class LimitedCountdownTest extends InstrumentationTestCase {

	public void testGetRemainingTime() throws Exception {
		LimitedCountdown countdown = new LimitedCountdown(10000L);
		Assert.assertEquals(0L, countdown.getRemainingTime(0L));
		Assert.assertEquals(0L, countdown.getRemainingTime(1000L));
		Assert.assertEquals(0L, countdown.getRemainingTime(100000L));
		countdown.start(0L);
		Assert.assertEquals(8000L, countdown.getRemainingTime(2000L));
		Assert.assertEquals(5000L, countdown.getRemainingTime(5000L));
		Assert.assertEquals(1L, countdown.getRemainingTime(9999L));
		Assert.assertEquals(0L, countdown.getRemainingTime(10000L));
		Assert.assertEquals(0L, countdown.getRemainingTime(10001L));
		Assert.assertEquals(0L, countdown.getRemainingTime(15000L));
		Assert.assertEquals(0L, countdown.getRemainingTime(123000L));
	}

	public void testGetTime() throws Exception {
		LimitedCountdown countdown = new LimitedCountdown(10000L);
		Assert.assertEquals(0L, countdown.getTime(0L));
		Assert.assertEquals(0L, countdown.getTime(1000L));
		Assert.assertEquals(0L, countdown.getTime(100000L));
		countdown.start(0L);
		Assert.assertEquals(2000L, countdown.getTime(2000L));
		Assert.assertEquals(5000L, countdown.getTime(5000L));
		Assert.assertEquals(9999L, countdown.getTime(9999L));
		Assert.assertEquals(10000L, countdown.getTime(10000L));
		Assert.assertEquals(10000L, countdown.getTime(10001L));
		Assert.assertEquals(10000L, countdown.getTime(15000L));
		Assert.assertEquals(10000L, countdown.getTime(123000L));
	}

	public void testRestart() throws Exception {
		LimitedCountdown countdown = new LimitedCountdown(10000L);
		countdown.start(0L);
		Assert.assertEquals(0L, countdown.getRemainingTime(20000L));
		countdown.restart(30000L);
		Assert.assertEquals(9000L, countdown.getRemainingTime(31000L));
		Assert.assertEquals(0L, countdown.getRemainingTime(40000L));
	}

	public void testFirstStartEqualsRestart() throws Exception {
		LimitedCountdown countdown1 = new LimitedCountdown(10000L);
		countdown1.start(30000L);
		Assert.assertEquals(9000L, countdown1.getRemainingTime(31000L));

		LimitedCountdown countdown2 = new LimitedCountdown(10000L);
		countdown2.restart(30000L);
		Assert.assertEquals(9000L, countdown2.getRemainingTime(31000L));
	}
}
