package cz.prvaak.throughtheagesclock.clock.counter;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.clock.counter.LimitedCounter} class.
 */
public class LimitedCounterTest extends InstrumentationTestCase {

	private static LimitedCounter createCounter() {
		return new LimitedCounter(new TimeAmount(10000L));
	}

	public void testGetTime() throws Exception {
		LimitedCounter counter = createCounter();
		counter.restart(new TimeInstant(0L));
		Assert.assertEquals(new TimeAmount(2000L), counter.getElapsedTime(new TimeInstant(2000L)));
		Assert.assertEquals(new TimeAmount(5000L), counter.getElapsedTime(new TimeInstant(5000L)));
		Assert.assertEquals(new TimeAmount(9999L), counter.getElapsedTime(new TimeInstant(9999L)));
		Assert.assertEquals(new TimeAmount(10000L), counter.getElapsedTime(new TimeInstant(10000L)));
		Assert.assertEquals(new TimeAmount(10000L), counter.getElapsedTime(new TimeInstant(10001L)));
		Assert.assertEquals(new TimeAmount(10000L), counter.getElapsedTime(new TimeInstant(15000L)));
		Assert.assertEquals(new TimeAmount(10000L), counter.getElapsedTime(new TimeInstant(123000L)));
	}

	public void testRestart() throws Exception {
		LimitedCounter counter = createCounter();
		counter.restart(new TimeInstant(0L));
		Assert.assertEquals(new TimeAmount(10000L), counter.getElapsedTime(new TimeInstant(20000L)));
		counter.restart(new TimeInstant(30000L));
		Assert.assertEquals(new TimeAmount(1000L), counter.getElapsedTime(new TimeInstant(31000L)));
		Assert.assertEquals(new TimeAmount(10000L), counter.getElapsedTime(new TimeInstant(40000L)));
	}

	public void testNegativeTimeLimitNotAllowed() throws Exception {
		try {
			new LimitedCounter(new TimeAmount(-5000));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}