package cz.prvaak.throughtheagesclock;

import android.test.InstrumentationTestCase;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.UpkeepTransition} class.
 */
public class UpkeepTransitionTest extends InstrumentationTestCase {

	public void testBeforeSwitch() throws Exception {
		FakePlayerClock player = new FakePlayerClock();
		UpkeepTransition transition = new UpkeepTransition();

		player.start(0L);
		assertTrue(player.isStarted);
		assertFalse(player.isUpkeepStarted);
		assertFalse(player.isReserveAdded);

		transition.beforeSwitch(player, 1000L);
		assertFalse(player.isStarted);
		assertTrue(player.isUpkeepStarted);
		assertTrue(player.isReserveAdded);
	}

	public void testAfterSwitch() throws Exception {
		FakePlayerClock player = new FakePlayerClock();
		UpkeepTransition transition = new UpkeepTransition();

		assertFalse(player.isStarted);
		assertFalse(player.isUpkeepStarted);
		assertFalse(player.isReserveAdded);

		transition.afterSwitch(player, 1000L);
		assertTrue(player.isStarted);
		assertFalse(player.isUpkeepStarted);
		assertFalse(player.isReserveAdded);
	}
}
