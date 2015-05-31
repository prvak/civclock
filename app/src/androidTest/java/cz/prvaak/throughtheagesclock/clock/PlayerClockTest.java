package cz.prvaak.throughtheagesclock.clock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;


/**
 * Tests of {@link PlayerClock} class.
 */
public class PlayerClockTest extends InstrumentationTestCase {

	/** Create player clock with 60 seconds for turn and 30 seconds for upkeep. */
	public PlayerClock createPlayerClock() {
		return new PlayerClock(new FakePlayerId(), new TimeAmount(60000L), new TimeAmount(30000L),
				new TimeAmount(30000L), new TimeAmount(10000L));
	}

	public void testGetRemainingTime() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		assertEquals(new TimeAmount(60000L), playerClock.getRemainingReserveTime(new TimeInstant(0L)));
	}

	public void testGetRemainingUpkeepTime() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		assertEquals(new TimeAmount(0L), playerClock.getRemainingUpkeepTime(new TimeInstant(1000L)));
		playerClock.upkeep(new TimeInstant(2000L));
		assertEquals(new TimeAmount(29000L), playerClock.getRemainingUpkeepTime(new TimeInstant(3000L)));
		assertEquals(new TimeAmount(28000L), playerClock.getRemainingUpkeepTime(new TimeInstant(4000L)));
		assertEquals(new TimeAmount(0L), playerClock.getRemainingUpkeepTime(new TimeInstant(65000L))); // upkeep time exceeded
	}

	public void testGetRemainingTimeAfterStart() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(new TimeInstant(1000L));
		assertEquals(new TimeAmount(60000L), playerClock.getRemainingReserveTime(new TimeInstant(1000L))); // initial time
		assertEquals(new TimeAmount(59000L), playerClock.getRemainingReserveTime(new TimeInstant(2000L)));
		assertEquals(new TimeAmount(-1000L), playerClock.getRemainingReserveTime(new TimeInstant(62000L))); // negative value is allowed
	}

	public void testGetRemainingTimeAfterStop() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(new TimeInstant(0L));
		playerClock.stop(new TimeInstant(1000L));
		assertEquals(new TimeAmount(59000L), playerClock.getRemainingReserveTime(new TimeInstant(2000L)));
		assertEquals(new TimeAmount(59000L), playerClock.getRemainingReserveTime(new TimeInstant(3000L))); // time is not decreasing
		assertEquals(new TimeAmount(59000L), playerClock.getRemainingReserveTime(new TimeInstant(100000L))); // time is not decreasing
	}

	public void testAddReserveTime() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.addReserveTime(new TimeInstant(0L), new TimeAmount(1000L));
		assertEquals(new TimeAmount(61000L), playerClock.getRemainingReserveTime(new TimeInstant(1000L)));
	}

	public void testAddUpkeepTime() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.addUpkeepTime(new TimeInstant(0L), new TimeAmount(5000L));
		assertEquals(new TimeAmount(4000L), playerClock.getRemainingUpkeepTime(new TimeInstant(1000L)));
		playerClock.upkeep(new TimeInstant(2000L));
		assertEquals(new TimeAmount(32000L), playerClock.getRemainingUpkeepTime(new TimeInstant(3000L)));
	}

	public void testAddTurnBonusTime() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.addTurnBonusTime(new TimeInstant(0L));
		assertEquals(new TimeAmount(90000L), playerClock.getRemainingReserveTime(new TimeInstant(1000L)));
	}

	public void testAddUpkeepBonusTime() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.addUpkeepBonusTime(new TimeInstant(0L));
		assertEquals(new TimeAmount(9000L), playerClock.getRemainingUpkeepTime(new TimeInstant(1000L)));
		playerClock.upkeep(new TimeInstant(2000L));
		assertEquals(new TimeAmount(37000L), playerClock.getRemainingUpkeepTime(new TimeInstant(3000L)));
	}

	public void testUpkeepStartShort() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.upkeep(new TimeInstant(0L));
		playerClock.start(new TimeInstant(10000L));
		assertEquals(new TimeAmount(60000L), playerClock.getRemainingReserveTime(new TimeInstant(20000L)));
	}

	public void testUpkeepStartLong() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.upkeep(new TimeInstant(0L));
		playerClock.start(new TimeInstant(10000L));
		assertEquals(new TimeAmount(50000L), playerClock.getRemainingReserveTime(new TimeInstant(40000L)));
	}

	public void testStartUpkeepShort() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(new TimeInstant(0L));
		playerClock.upkeep(new TimeInstant(10000L));
		assertEquals(new TimeAmount(50000L), playerClock.getRemainingReserveTime(new TimeInstant(20000L)));
	}

	public void testStartUpkeepLong() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(new TimeInstant(0L));
		playerClock.upkeep(new TimeInstant(10000L));
		assertEquals(new TimeAmount(40000L), playerClock.getRemainingReserveTime(new TimeInstant(50000L)));
	}

	public void testGetRemainingUpkeepTimeAfterRepeatedStart() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(new TimeInstant(0L));
		playerClock.stop(new TimeInstant(1000L));
		playerClock.upkeep(new TimeInstant(2000L));
		playerClock.start(new TimeInstant(3000L));
		assertEquals(new TimeAmount(59000L), playerClock.getRemainingReserveTime(new TimeInstant(3000L))); // time is not decreasing
		assertEquals(new TimeAmount(28000L), playerClock.getRemainingUpkeepTime(new TimeInstant(4000L)));
		assertEquals(new TimeAmount(59000L), playerClock.getRemainingReserveTime(new TimeInstant(5000L))); // time is not decreasing
		assertEquals(new TimeAmount(58000L), playerClock.getRemainingReserveTime(new TimeInstant(33000L))); // upkeep protection exhausted
	}

	public void testUpkeepStartStopShort() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.upkeep(new TimeInstant(0L));
		playerClock.start(new TimeInstant(1000L));
		playerClock.stop(new TimeInstant(2000L));
		assertEquals(new TimeAmount(60000L), playerClock.getRemainingReserveTime(new TimeInstant(3000L))); // time is not decreasing
		assertEquals(new TimeAmount(26000L), playerClock.getRemainingUpkeepTime(new TimeInstant(4000L)));
	}

	public void testUpkeepStartStopLong() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.upkeep(new TimeInstant(0L));
		playerClock.start(new TimeInstant(1000L));
		playerClock.stop(new TimeInstant(40000L));
		assertEquals(new TimeAmount(50000L), playerClock.getRemainingReserveTime(new TimeInstant(40000L)));
		assertEquals(new TimeAmount(0L), playerClock.getRemainingUpkeepTime(new TimeInstant(40000L)));
	}

	public void testStartUpkeepStopShort() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(new TimeInstant(0L));
		playerClock.upkeep(new TimeInstant(1000L));
		playerClock.stop(new TimeInstant(2000L));
		assertEquals(new TimeAmount(59000L), playerClock.getRemainingReserveTime(new TimeInstant(3000L))); // time is not decreasing
		assertEquals(new TimeAmount(27000L), playerClock.getRemainingUpkeepTime(new TimeInstant(4000L)));
	}

	public void testStartUpkeepStopLong() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(new TimeInstant(0L));
		playerClock.upkeep(new TimeInstant(1000L));
		playerClock.stop(new TimeInstant(50000L));
		assertEquals(new TimeAmount(40000L), playerClock.getRemainingReserveTime(new TimeInstant(50000L)));
		assertEquals(new TimeAmount(0L), playerClock.getRemainingUpkeepTime(new TimeInstant(50000L)));
	}

	public void testStartUpkeepPauseResume() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(new TimeInstant(1000L));
		playerClock.upkeep(new TimeInstant(2000L));
		playerClock.pause(new TimeInstant(3000L));
		assertEquals(new TimeAmount(59000L), playerClock.getRemainingReserveTime(new TimeInstant(4000L)));
		assertEquals(new TimeAmount(29000L), playerClock.getRemainingUpkeepTime(new TimeInstant(4000L)));
		assertEquals(new TimeAmount(59000L), playerClock.getRemainingReserveTime(new TimeInstant(60000L)));
		assertEquals(new TimeAmount(29000L), playerClock.getRemainingUpkeepTime(new TimeInstant(60000L)));
		playerClock.resume(new TimeInstant(60000L));
		assertEquals(new TimeAmount(1000L), playerClock.getRemainingUpkeepTime(new TimeInstant(88000L)));
		assertEquals(new TimeAmount(58000L), playerClock.getRemainingReserveTime(new TimeInstant(90000L)));
	}

	public void testStartPauseResume() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.start(new TimeInstant(1000L));
		playerClock.pause(new TimeInstant(3000L));
		assertEquals(new TimeAmount(58000L), playerClock.getRemainingReserveTime(new TimeInstant(4000L)));
		assertEquals(new TimeAmount(0L), playerClock.getRemainingUpkeepTime(new TimeInstant(4000L)));
		assertEquals(new TimeAmount(58000L), playerClock.getRemainingReserveTime(new TimeInstant(60000L)));
		assertEquals(new TimeAmount(0L), playerClock.getRemainingUpkeepTime(new TimeInstant(60000L)));
		playerClock.resume(new TimeInstant(60000L));
		assertEquals(new TimeAmount(0L), playerClock.getRemainingUpkeepTime(new TimeInstant(70000L)));
		assertEquals(new TimeAmount(28000L), playerClock.getRemainingReserveTime(new TimeInstant(90000L)));
	}

	public void testNegativeUpkeepTimeNotAllowed() throws Exception {
		try {
			new PlayerClock(new FakePlayerId(), new TimeAmount(60000), new TimeAmount(-5000),
					new TimeAmount(20000), new TimeAmount(10000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testNegativeTurnBonusTimeNotAllowed() throws Exception {
		try {
			new PlayerClock(new FakePlayerId(), new TimeAmount(60000), new TimeAmount(5000),
					new TimeAmount(-20000), new TimeAmount(10000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testNegativeUpkeepBonusTimeNotAllowed() throws Exception {
		try {
			new PlayerClock(new FakePlayerId(), new TimeAmount(60000), new TimeAmount(5000),
					new TimeAmount(20000), new TimeAmount(-10000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testPauseResumeOfStopped() throws Exception {
		PlayerClock playerClock = createPlayerClock();
		playerClock.pause(new TimeInstant(1000L));
		playerClock.resume(new TimeInstant(2000L));
		playerClock.pause(new TimeInstant(3000L));
		playerClock.resume(new TimeInstant(4000L));
		assertEquals(new TimeAmount(60000L), playerClock.getRemainingReserveTime(new TimeInstant(5000L)));
		assertEquals(new TimeAmount(0L), playerClock.getRemainingUpkeepTime(new TimeInstant(5000L)));
	}
}
