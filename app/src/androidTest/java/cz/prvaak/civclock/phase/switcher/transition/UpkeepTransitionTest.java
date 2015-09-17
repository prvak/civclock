package cz.prvaak.civclock.phase.switcher.transition;

import android.test.InstrumentationTestCase;

import cz.prvaak.civclock.TimeInstant;
import cz.prvaak.civclock.clock.FakeEpoch;
import cz.prvaak.civclock.clock.FakePlayerClock;

/**
 * Tests of {@link cz.prvaak.civclock.phase.switcher.transition.UpkeepTransition} class.
 */
public class UpkeepTransitionTest extends InstrumentationTestCase {

	public void testBeforeSwitch() throws Exception {
		FakePlayerClock player = new FakePlayerClock();
		UpkeepTransition transition = new UpkeepTransition();

		player.start(new TimeInstant(0L));
		assertTrue(player.isStarted);
		assertFalse(player.isUpkeepStarted);
		assertFalse(player.isReserveAdded);

		transition.beforeSwitch(player, new TimeInstant(1000L), FakeEpoch.ONE);
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

		transition.afterSwitch(player, new TimeInstant(1000L), FakeEpoch.ONE);
		assertTrue(player.isStarted);
		assertFalse(player.isUpkeepStarted);
		assertFalse(player.isReserveAdded);
	}
}
