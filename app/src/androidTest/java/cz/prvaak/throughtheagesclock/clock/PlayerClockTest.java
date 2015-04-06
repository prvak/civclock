package cz.prvaak.throughtheagesclock.clock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;


/**
 * Tests of {@link PlayerClock} class.
 */
public class PlayerClockTest extends InstrumentationTestCase {

	/** Create player clock with 60 seconds for turn and 30 seconds for upkeep. */
	public PlayerClock createPlayerClock() {
		return new PlayerClock(60000L, 30000L);
	}

	public void testGetRemainingTime() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		assertEquals(60000L, playerClock.getRemainingReserveTime(0L));
	}

	public void testGetRemainingUpkeepTime() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		assertEquals(0L, playerClock.getRemainingUpkeepTime(1000L));
		playerClock.upkeep(2000L);
		assertEquals(29000L, playerClock.getRemainingUpkeepTime(3000L));
		assertEquals(28000L, playerClock.getRemainingUpkeepTime(4000L));
		assertEquals(0L, playerClock.getRemainingUpkeepTime(65000L)); // upkeep time exceeded
	}

	public void testGetRemainingTimeAfterStart() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(1000L);
		assertEquals(60000L, playerClock.getRemainingReserveTime(1000L)); // initial time
		assertEquals(59000L, playerClock.getRemainingReserveTime(2000L));
		assertEquals(-1000L, playerClock.getRemainingReserveTime(62000L)); // negative value is allowed
	}

	public void testGetRemainingTimeAfterStop() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(0L);
		playerClock.stop(1000L);
		assertEquals(59000L, playerClock.getRemainingReserveTime(2000L));
		assertEquals(59000L, playerClock.getRemainingReserveTime(3000L)); // time is not decreasing
		assertEquals(59000L, playerClock.getRemainingReserveTime(100000L)); // time is not decreasing
	}

	public void testAdd() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.addReserveTime(1000L);
		assertEquals(61000L, playerClock.getRemainingReserveTime(1000L));
	}

	public void testUpkeepStartShort() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.upkeep(0L);
		playerClock.start(10000L);
		assertEquals(60000L, playerClock.getRemainingReserveTime(20000L));
	}

	public void testUpkeepStartLong() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.upkeep(0L);
		playerClock.start(10000L);
		assertEquals(50000L, playerClock.getRemainingReserveTime(40000L));
	}

	public void testStartUpkeepShort() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(0L);
		playerClock.upkeep(10000L);
		assertEquals(50000L, playerClock.getRemainingReserveTime(20000L));
	}

	public void testStartUpkeepLong() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(0L);
		playerClock.upkeep(10000L);
		assertEquals(40000L, playerClock.getRemainingReserveTime(50000L));
	}

	public void testGetRemainingUpkeepTimeAfterRepeatedStart() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(0L);
		playerClock.stop(1000L);
		playerClock.upkeep(2000L);
		playerClock.start(3000L);
		assertEquals(59000L, playerClock.getRemainingReserveTime(3000L)); // time is not decreasing
		assertEquals(28000L, playerClock.getRemainingUpkeepTime(4000L));
		assertEquals(59000L, playerClock.getRemainingReserveTime(5000L)); // time is not decreasing
		assertEquals(58000L, playerClock.getRemainingReserveTime(33000L)); // upkeep protection exhausted
	}

	public void testUpkeepStartStopShort() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.upkeep(0L);
		playerClock.start(1000L);
		playerClock.stop(2000L);
		assertEquals(60000L, playerClock.getRemainingReserveTime(3000L)); // time is not decreasing
		assertEquals(26000L, playerClock.getRemainingUpkeepTime(4000L));
	}

	public void testUpkeepStartStopLong() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.upkeep(0L);
		playerClock.start(1000L);
		playerClock.stop(40000L);
		assertEquals(50000L, playerClock.getRemainingReserveTime(40000L));
		assertEquals(0L, playerClock.getRemainingUpkeepTime(40000L));
	}

	public void testStartUpkeepStopShort() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(0L);
		playerClock.upkeep(1000L);
		playerClock.stop(2000L);
		assertEquals(59000L, playerClock.getRemainingReserveTime(3000L)); // time is not decreasing
		assertEquals(27000L, playerClock.getRemainingUpkeepTime(4000L));
	}

	public void testStartUpkeepStopLong() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(0L);
		playerClock.upkeep(1000L);
		playerClock.stop(50000L);
		assertEquals(40000L, playerClock.getRemainingReserveTime(50000L));
		assertEquals(0L, playerClock.getRemainingUpkeepTime(50000L));
	}

	public void testStartUpkeepPauseResume() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(1000L);
		playerClock.upkeep(2000L);
		playerClock.pause(3000L);
		assertEquals(59000L, playerClock.getRemainingReserveTime(4000L));
		assertEquals(29000L, playerClock.getRemainingUpkeepTime(4000L));
		assertEquals(59000L, playerClock.getRemainingReserveTime(60000L));
		assertEquals(29000L, playerClock.getRemainingUpkeepTime(60000L));
		playerClock.resume(60000L);
		assertEquals(1000L, playerClock.getRemainingUpkeepTime(88000L));
		assertEquals(58000L, playerClock.getRemainingReserveTime(90000L));
	}

	public void testStartPauseResume() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(1000L);
		playerClock.pause(3000L);
		assertEquals(58000L, playerClock.getRemainingReserveTime(4000L));
		assertEquals(0L, playerClock.getRemainingUpkeepTime(4000L));
		assertEquals(58000L, playerClock.getRemainingReserveTime(60000L));
		assertEquals(0L, playerClock.getRemainingUpkeepTime(60000L));
		playerClock.resume(60000L);
		assertEquals(0L, playerClock.getRemainingUpkeepTime(70000L));
		assertEquals(28000L, playerClock.getRemainingReserveTime(90000L));
	}

	public void testNegativeUpkeepTimeNotAllowed() throws Exception {
		try {
			new PlayerClock(60000, -5000);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
