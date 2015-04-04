package cz.prvaak.throughtheagesclock.clock.timer.adapter;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import cz.prvaak.throughtheagesclock.clock.timer.Timer;

/**
 * Tests of {@link LimitedTimer} class.
 */
public class LimitedTimerTest extends InstrumentationTestCase {

	public void testGetTime() throws Exception {
		LimitedTimer timer = new LimitedTimer(new Timer(), 10000L);
		timer.start(0L);
		Assert.assertEquals(2000L, timer.getTime(2000L));
		Assert.assertEquals(5000L, timer.getTime(5000L));
		Assert.assertEquals(9999L, timer.getTime(9999L));
		Assert.assertEquals(10000L, timer.getTime(10000L));
		Assert.assertEquals(10000L, timer.getTime(10001L));
		Assert.assertEquals(10000L, timer.getTime(15000L));
		Assert.assertEquals(10000L, timer.getTime(123000L));
	}

	public void testRestart() throws Exception {
		LimitedTimer timer = new LimitedTimer(new Timer(), 10000L);
		timer.start(0L);
		Assert.assertEquals(10000L, timer.getTime(20000L));
		timer.start(30000L);
		Assert.assertEquals(1000L, timer.getTime(31000L));
		Assert.assertEquals(10000L, timer.getTime(40000L));
	}
}