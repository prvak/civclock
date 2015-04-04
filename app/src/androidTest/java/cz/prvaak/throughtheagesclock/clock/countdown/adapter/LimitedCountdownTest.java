package cz.prvaak.throughtheagesclock.clock.countdown.adapter;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import cz.prvaak.throughtheagesclock.clock.timer.Timer;
import cz.prvaak.throughtheagesclock.clock.timer.adapter.LimitedTimer;

/**
 * Tests of {@link LimitedCountdown} class.
 */
public class LimitedCountdownTest extends InstrumentationTestCase {

	public void testGetRemainingTime() throws Exception {
		LimitedCountdown countdown = new LimitedCountdown(10000L);
		countdown.start(0L);
		Assert.assertEquals(8000L, countdown.getRemainingTime(2000L));
		Assert.assertEquals(5000L, countdown.getRemainingTime(5000L));
		Assert.assertEquals(1L, countdown.getRemainingTime(9999L));
		Assert.assertEquals(0L, countdown.getRemainingTime(10000L));
		Assert.assertEquals(0L, countdown.getRemainingTime(10001L));
		Assert.assertEquals(0L, countdown.getRemainingTime(15000L));
		Assert.assertEquals(0L, countdown.getRemainingTime(123000L));
	}

	public void testRestart() throws Exception {
		LimitedCountdown countdown = new LimitedCountdown(10000L);
		countdown.start(0L);
		Assert.assertEquals(0L, countdown.getRemainingTime(20000L));
		countdown.start(30000L);
		Assert.assertEquals(9000L, countdown.getRemainingTime(31000L));
		Assert.assertEquals(0L, countdown.getRemainingTime(40000L));
	}

}
