package cz.prvaak.throughtheagesclock.clock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import cz.prvaak.throughtheagesclock.player.PlayerCountdown;


/**
 * Tests of {@link cz.prvaak.throughtheagesclock.player.PlayerCountdown} class.
 */
public class PlayerCountdownTest extends InstrumentationTestCase {

	/** Create player clock with 60 seconds for turn and 30 seconds for upkeep. */
	public PlayerCountdown createPlayerClock() {
		return new PlayerCountdown(60000L, 30000L);
	}

	public void testGetRemainingTime() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		assertEquals(60000L, playerCountdown.getRemainingTime(0L));
	}

	public void testGetRemainingUpkeepTime() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		assertEquals(0L, playerCountdown.getRemainingUpkeepTime(1000L));
		playerCountdown.upkeep(2000L);
		assertEquals(29000L, playerCountdown.getRemainingUpkeepTime(3000L));
		assertEquals(28000L, playerCountdown.getRemainingUpkeepTime(4000L));
		assertEquals(0L, playerCountdown.getRemainingUpkeepTime(65000L)); // upkeep time exceeded
	}

	public void testGetRemainingTimeAfterStart() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.start(1000L);
		assertEquals(60000L, playerCountdown.getRemainingTime(1000L)); // initial time
		assertEquals(59000L, playerCountdown.getRemainingTime(2000L));
		assertEquals(-1000L, playerCountdown.getRemainingTime(62000L)); // negative value is allowed
	}

	public void testGetRemainingTimeAfterStop() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.start(0L);
		playerCountdown.stop(1000L);
		assertEquals(59000L, playerCountdown.getRemainingTime(2000L));
		assertEquals(59000L, playerCountdown.getRemainingTime(3000L)); // time is not decreasing
		assertEquals(59000L, playerCountdown.getRemainingTime(100000L)); // time is not decreasing
	}

	public void testAdd() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.addTime(1000L);
		assertEquals(61000L, playerCountdown.getRemainingTime(1000L));
	}

	public void testUpkeepStartShort() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.upkeep(0L);
		playerCountdown.start(10000L);
		assertEquals(60000L, playerCountdown.getRemainingTime(20000L));
	}

	public void testUpkeepStartLong() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.upkeep(0L);
		playerCountdown.start(10000L);
		assertEquals(50000L, playerCountdown.getRemainingTime(40000L));
	}

	public void testStartUpkeepShort() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.start(0L);
		playerCountdown.upkeep(10000L);
		assertEquals(50000L, playerCountdown.getRemainingTime(20000L));
	}

	public void testStartUpkeepLong() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.start(0L);
		playerCountdown.upkeep(10000L);
		assertEquals(40000L, playerCountdown.getRemainingTime(50000L));
	}

	public void testGetRemainingUpkeepTimeAfterRepeatedStart() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.start(0L);
		playerCountdown.stop(1000L);
		playerCountdown.upkeep(2000L);
		playerCountdown.start(3000L);
		assertEquals(59000L, playerCountdown.getRemainingTime(3000L)); // time is not decreasing
		assertEquals(28000L, playerCountdown.getRemainingUpkeepTime(4000L));
		assertEquals(59000L, playerCountdown.getRemainingTime(5000L)); // time is not decreasing
		assertEquals(58000L, playerCountdown.getRemainingTime(33000L)); // upkeep protection exhausted
	}

	public void testUpkeepStartStopShort() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.upkeep(0L);
		playerCountdown.start(1000L);
		playerCountdown.stop(2000L);
		assertEquals(60000L, playerCountdown.getRemainingTime(3000L)); // time is not decreasing
		assertEquals(26000L, playerCountdown.getRemainingUpkeepTime(4000L));
	}

	public void testUpkeepStartStopLong() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.upkeep(0L);
		playerCountdown.start(1000L);
		playerCountdown.stop(40000L);
		assertEquals(50000L, playerCountdown.getRemainingTime(40000L));
		assertEquals(0L, playerCountdown.getRemainingUpkeepTime(40000L));
	}

	public void testStartUpkeepStopShort() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.start(0L);
		playerCountdown.upkeep(1000L);
		playerCountdown.stop(2000L);
		assertEquals(59000L, playerCountdown.getRemainingTime(3000L)); // time is not decreasing
		assertEquals(27000L, playerCountdown.getRemainingUpkeepTime(4000L));
	}

	public void testStartUpkeepStopLong() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.start(0L);
		playerCountdown.upkeep(1000L);
		playerCountdown.stop(50000L);
		assertEquals(40000L, playerCountdown.getRemainingTime(50000L));
		assertEquals(0L, playerCountdown.getRemainingUpkeepTime(50000L));
	}

	public void testStartUpkeepPauseResume() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.start(1000L);
		playerCountdown.upkeep(2000L);
		playerCountdown.pause(3000L);
		assertEquals(59000L, playerCountdown.getRemainingTime(4000L));
		assertEquals(29000L, playerCountdown.getRemainingUpkeepTime(4000L));
		assertEquals(59000L, playerCountdown.getRemainingTime(60000L));
		assertEquals(29000L, playerCountdown.getRemainingUpkeepTime(60000L));
		playerCountdown.resume(60000L);
		assertEquals(1000L, playerCountdown.getRemainingUpkeepTime(88000L));
		assertEquals(58000L, playerCountdown.getRemainingTime(90000L));
	}

	public void testStartPauseResume() throws Exception {
		PlayerCountdown playerCountdown = createPlayerClock();
		playerCountdown.start(1000L);
		playerCountdown.pause(3000L);
		assertEquals(58000L, playerCountdown.getRemainingTime(4000L));
		assertEquals(0L, playerCountdown.getRemainingUpkeepTime(4000L));
		assertEquals(58000L, playerCountdown.getRemainingTime(60000L));
		assertEquals(0L, playerCountdown.getRemainingUpkeepTime(60000L));
		playerCountdown.resume(60000L);
		assertEquals(0L, playerCountdown.getRemainingUpkeepTime(70000L));
		assertEquals(28000L, playerCountdown.getRemainingTime(90000L));
	}

	public void testNegativeUpkeepTimeNotAllowed() throws Exception {
		try {
			new PlayerCountdown(60000, -5000);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
