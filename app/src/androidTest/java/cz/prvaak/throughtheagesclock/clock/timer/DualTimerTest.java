package cz.prvaak.throughtheagesclock.clock.timer;

import android.test.InstrumentationTestCase;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.clock.timer.DualTimer} class.
 */
public class DualTimerTest extends InstrumentationTestCase {

	private DualTimer createTimer() {
		return new DualTimer(new TimeAmount(10000L));
	}

	public void testRestart() throws Exception {
		DualTimer timer = createTimer();
		timer.addTime(new TimeInstant(0L), new TimeAmount(5000L));
		timer.start(new TimeInstant(0L));

		TimeInstant now = new TimeInstant(10000L);
		assertEquals(new TimeAmount(5000L), timer.getRemainingTime(now));
		timer.restart(now); // This should refill the fixed time only.
		assertEquals(new TimeAmount(10000L), timer.getRemainingTime(now));
	}

	public void testRestartSoon() throws Exception {
		DualTimer timer = createTimer();
		timer.addTime(new TimeInstant(0L), new TimeAmount(5000L));
		timer.start(new TimeInstant(0L));

		TimeInstant now = new TimeInstant(2000L);
		assertEquals(new TimeAmount(13000L), timer.getRemainingTime(now));
		timer.restart(now);
		assertEquals(new TimeAmount(13000L), timer.getRemainingTime(now));
	}

	public void testAddAndRestart() throws Exception {
		DualTimer timer = createTimer();
		timer.addTime(new TimeInstant(0L), new TimeAmount(5000L));
		timer.start(new TimeInstant(0L));

		TimeInstant now = new TimeInstant(10000L);
		assertEquals(new TimeAmount(5000L), timer.getRemainingTime(now));
		timer.addTime(now, new TimeAmount(10000L));
		timer.restart(now);
		assertEquals(new TimeAmount(20000L), timer.getRemainingTime(now));
	}

	public void testRestartAndAdd() throws Exception {
		DualTimer timer = createTimer();
		timer.addTime(new TimeInstant(0L), new TimeAmount(5000L));
		timer.start(new TimeInstant(0L));

		TimeInstant now = new TimeInstant(10000L);
		assertEquals(new TimeAmount(5000L), timer.getRemainingTime(now));
		timer.restart(now);
		timer.addTime(now, new TimeAmount(10000L));
		assertEquals(new TimeAmount(20000L), timer.getRemainingTime(now));
	}

	public void testAddAndStartLater() throws Exception {
		DualTimer timer = createTimer();
		timer.addTime(new TimeInstant(0L), new TimeAmount(5000L));
		timer.start(new TimeInstant(1000L));

		TimeInstant now = new TimeInstant(11000L);
		assertEquals(new TimeAmount(5000L), timer.getRemainingTime(now));
		timer.restart(now);
		timer.addTime(now, new TimeAmount(10000L));
		assertEquals(new TimeAmount(20000L), timer.getRemainingTime(now));
	}
}
