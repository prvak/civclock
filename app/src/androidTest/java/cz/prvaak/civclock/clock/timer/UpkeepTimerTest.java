package cz.prvaak.civclock.clock.timer;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import cz.prvaak.civclock.TimeAmount;
import cz.prvaak.civclock.TimeInstant;

/**
 * Tests of {@link UpkeepTimer} class.
 */
public class UpkeepTimerTest extends InstrumentationTestCase {

	private UpkeepTimer createTimer() {
		return new UpkeepTimer(new TimeAmount(10000L));
	}

	public void testRestart() throws Exception {
		UpkeepTimer timer = createTimer();
		timer.addTime(new TimeInstant(0L), new TimeAmount(5000L));
		timer.start(new TimeInstant(0L));

		TimeInstant now = new TimeInstant(10000L);
		assertEquals(new TimeAmount(5000L), timer.getRemainingTime(now));
		timer.restart(now); // This should refill the fixed time only.
		assertEquals(new TimeAmount(10000L), timer.getRemainingTime(now));
	}

	public void testRestartSoon() throws Exception {
		UpkeepTimer timer = createTimer();
		timer.addTime(new TimeInstant(0L), new TimeAmount(5000L));
		timer.start(new TimeInstant(0L));

		TimeInstant now = new TimeInstant(2000L);
		assertEquals(new TimeAmount(13000L), timer.getRemainingTime(now));
		timer.restart(now);
		assertEquals(new TimeAmount(13000L), timer.getRemainingTime(now));
	}

	public void testAddAndRestart() throws Exception {
		UpkeepTimer timer = createTimer();
		timer.addTime(new TimeInstant(0L), new TimeAmount(5000L));
		timer.start(new TimeInstant(0L));

		TimeInstant now = new TimeInstant(10000L);
		assertEquals(new TimeAmount(5000L), timer.getRemainingTime(now));
		timer.addTime(now, new TimeAmount(10000L));
		timer.restart(now);
		assertEquals(new TimeAmount(20000L), timer.getRemainingTime(now));
	}

	public void testRestartAndAdd() throws Exception {
		UpkeepTimer timer = createTimer();
		timer.addTime(new TimeInstant(0L), new TimeAmount(5000L));
		timer.start(new TimeInstant(0L));

		TimeInstant now = new TimeInstant(10000L);
		assertEquals(new TimeAmount(5000L), timer.getRemainingTime(now));
		timer.restart(now);
		timer.addTime(now, new TimeAmount(10000L));
		assertEquals(new TimeAmount(20000L), timer.getRemainingTime(now));
	}

	public void testAddAndStartLater() throws Exception {
		UpkeepTimer timer = createTimer();
		timer.addTime(new TimeInstant(0L), new TimeAmount(5000L));
		timer.start(new TimeInstant(1000L));

		TimeInstant now = new TimeInstant(11000L);
		assertEquals(new TimeAmount(5000L), timer.getRemainingTime(now));
		timer.restart(now);
		timer.addTime(now, new TimeAmount(10000L));
		assertEquals(new TimeAmount(20000L), timer.getRemainingTime(now));
	}

	public void testGetRemainingTimeOfStopped() throws Exception {
		UpkeepTimer timer = createTimer();
		assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(0L)));
		assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(1000L)));
	}

	public void testGetRemainingTimeOfEmpty() throws Exception {
		UpkeepTimer timer = new UpkeepTimer(new TimeAmount(0L));
		timer.start(new TimeInstant(1000L));
		assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(1000L)));
		assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(2000L)));
	}

	public void testNegativeFixedTimeNotAllowed() throws Exception {
		try {
			new UpkeepTimer(new TimeAmount(-5000));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
