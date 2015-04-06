package cz.prvaak.throughtheagesclock.clock.timer;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.clock.timer.LimitedTimer} class.
 */
public class LimitedTimerTest extends InstrumentationTestCase {

	public void testGetRemainingTime() throws Exception {
		LimitedTimer countdown = new LimitedTimer(10000L);
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
		LimitedTimer countdown = new LimitedTimer(10000L);
		Assert.assertEquals(0L, countdown.getElapsedTime(0L));
		Assert.assertEquals(0L, countdown.getElapsedTime(1000L));
		Assert.assertEquals(0L, countdown.getElapsedTime(100000L));
		countdown.start(0L);
		Assert.assertEquals(2000L, countdown.getElapsedTime(2000L));
		Assert.assertEquals(5000L, countdown.getElapsedTime(5000L));
		Assert.assertEquals(9999L, countdown.getElapsedTime(9999L));
		Assert.assertEquals(10000L, countdown.getElapsedTime(10000L));
		Assert.assertEquals(10000L, countdown.getElapsedTime(10001L));
		Assert.assertEquals(10000L, countdown.getElapsedTime(15000L));
		Assert.assertEquals(10000L, countdown.getElapsedTime(123000L));
	}

	public void testRestart() throws Exception {
		LimitedTimer countdown = new LimitedTimer(10000L);
		countdown.start(0L);
		Assert.assertEquals(0L, countdown.getRemainingTime(20000L));
		countdown.restart(30000L);
		Assert.assertEquals(9000L, countdown.getRemainingTime(31000L));
		Assert.assertEquals(0L, countdown.getRemainingTime(40000L));
	}

	public void testFirstStartEqualsRestart() throws Exception {
		LimitedTimer countdown1 = new LimitedTimer(10000L);
		countdown1.start(30000L);
		Assert.assertEquals(9000L, countdown1.getRemainingTime(31000L));

		LimitedTimer countdown2 = new LimitedTimer(10000L);
		countdown2.restart(30000L);
		Assert.assertEquals(9000L, countdown2.getRemainingTime(31000L));
	}
}
