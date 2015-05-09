package cz.prvaak.throughtheagesclock.phase.switcher.transition;

import android.test.InstrumentationTestCase;

import cz.prvaak.throughtheagesclock.clock.FakePlayerClock;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.phase.switcher.transition.NormalTransition} class.
 */
public class NormalTransitionTest extends InstrumentationTestCase {

	public void testBeforeSwitch() throws Exception {
		FakePlayerClock player = new FakePlayerClock();
		NormalTransition transition = new NormalTransition();

		player.start(0L);
		assertTrue(player.isStarted);
		assertFalse(player.isUpkeepStarted);
		assertFalse(player.isReserveAdded);

		transition.beforeSwitch(player, 1000L);
		assertFalse(player.isStarted);
		assertFalse(player.isUpkeepStarted);
		assertFalse(player.isReserveAdded);
	}

	public void testAfterSwitch() throws Exception {
		FakePlayerClock player = new FakePlayerClock();
		NormalTransition transition = new NormalTransition();

		assertFalse(player.isStarted);
		assertFalse(player.isUpkeepStarted);
		assertFalse(player.isReserveAdded);

		transition.afterSwitch(player, 1000L);
		assertTrue(player.isStarted);
		assertFalse(player.isUpkeepStarted);
		assertFalse(player.isReserveAdded);
	}
}