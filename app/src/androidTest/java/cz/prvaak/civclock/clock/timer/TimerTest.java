package cz.prvaak.civclock.clock.timer;

import android.test.InstrumentationTestCase;

import cz.prvaak.civclock.TimeAmount;
import cz.prvaak.civclock.TimeInstant;

/**
 * Tests of {@link Timer} class.
 */
public class TimerTest extends InstrumentationTestCase {

	private static Timer createTimer() {
		return new Timer(new TimeAmount(10000L));
	}

	public void testGetRemainingTime() throws Exception {
		Timer timer = createTimer();
		timer.restart(new TimeInstant(0L));
		assertEquals(new TimeAmount(9000L), timer.getRemainingTime(new TimeInstant(1000L)));
		assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(10000L)));
		assertEquals(new TimeAmount(-1000L), timer.getRemainingTime(new TimeInstant(11000L)));
	}

	public void testRestart() throws Exception {
		Timer timer = new Timer(new TimeAmount(10000L));
		timer.start(new TimeInstant(0L));
		timer.restart(new TimeInstant(1000L));
		assertEquals(new TimeAmount(9000L), timer.getRemainingTime(new TimeInstant(2000L)));
		assertEquals(new TimeAmount(0L), timer.getRemainingTime(new TimeInstant(11000L)));
		assertEquals(new TimeAmount(-1000L), timer.getRemainingTime(new TimeInstant(12000L)));
	}

	public void testRestartWithNewLimit() throws Exception {
		Timer timer = new Timer(new TimeAmount(10000L));
		timer.start(new TimeInstant(0L));
		timer.restart(new TimeInstant(1000L), new TimeAmount(20000L));
		assertEquals(new TimeAmount(19000L), timer.getRemainingTime(new TimeInstant(2000L)));
		assertEquals(new TimeAmount(10000L), timer.getRemainingTime(new TimeInstant(11000L)));
		assertEquals(new TimeAmount(9000L), timer.getRemainingTime(new TimeInstant(12000L)));
		assertEquals(new TimeAmount(-1000L), timer.getRemainingTime(new TimeInstant(22000L)));
	}
}
