package cz.prvaak.throughtheagesclock.clock.timer;

import android.test.InstrumentationTestCase;

/**
 * Tests of {@link Timer} class.
 */
public class TimerTest extends InstrumentationTestCase {

	public void testGetRemainingTime() throws Exception {
		Timer remainingTime = new Timer(10000L);
		remainingTime.restart(0L);
		assertEquals(9000L, remainingTime.getRemainingTime(1000L));
		assertEquals(0L, remainingTime.getRemainingTime(10000L));
		assertEquals(-1000L, remainingTime.getRemainingTime(11000L));
	}

	public void testRestart() throws Exception {
		Timer timer = new Timer(10000L);
		timer.start(0L);
		timer.restart(1000L);
		assertEquals(9000L, timer.getRemainingTime(2000L));
		assertEquals(0L, timer.getRemainingTime(11000L));
		assertEquals(-1000L, timer.getRemainingTime(12000L));
	}

	public void testRestartWithNewLimit() throws Exception {
		Timer timer = new Timer(10000L);
		timer.start(0L);
		timer.restart(1000L, 20000L);
		assertEquals(19000L, timer.getRemainingTime(2000L));
		assertEquals(10000L, timer.getRemainingTime(11000L));
		assertEquals(9000L, timer.getRemainingTime(12000L));
		assertEquals(-1000L, timer.getRemainingTime(22000L));
	}
}
