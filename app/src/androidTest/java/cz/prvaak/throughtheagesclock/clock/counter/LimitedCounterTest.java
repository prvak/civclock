package cz.prvaak.throughtheagesclock.clock.counter;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.clock.counter.LimitedCounter} class.
 */
public class LimitedCounterTest extends InstrumentationTestCase {

	public void testGetTime() throws Exception {
		LimitedCounter timer = new LimitedCounter(new Counter(), 10000L);
		timer.restart(0L);
		Assert.assertEquals(2000L, timer.getElapsedTime(2000L));
		Assert.assertEquals(5000L, timer.getElapsedTime(5000L));
		Assert.assertEquals(9999L, timer.getElapsedTime(9999L));
		Assert.assertEquals(10000L, timer.getElapsedTime(10000L));
		Assert.assertEquals(10000L, timer.getElapsedTime(10001L));
		Assert.assertEquals(10000L, timer.getElapsedTime(15000L));
		Assert.assertEquals(10000L, timer.getElapsedTime(123000L));
	}

	public void testRestart() throws Exception {
		LimitedCounter timer = new LimitedCounter(new Counter(), 10000L);
		timer.restart(0L);
		Assert.assertEquals(10000L, timer.getElapsedTime(20000L));
		timer.restart(30000L);
		Assert.assertEquals(1000L, timer.getElapsedTime(31000L));
		Assert.assertEquals(10000L, timer.getElapsedTime(40000L));
	}
}