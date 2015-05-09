package cz.prvaak.throughtheagesclock.phase.switcher;

import android.test.InstrumentationTestCase;

import java.util.List;

import cz.prvaak.throughtheagesclock.clock.FakePlayerClock;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.phase.RoundAboutPhase;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.phase.RoundAboutPhase} class.
 */
public class RoundAboutPhaseTest extends InstrumentationTestCase {

	public void testFirstPlayer() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		RoundAboutPhase phase = new RoundAboutPhase(allPlayers, allPlayers.get(2));

		assertEquals(allPlayers.get(2), phase.getCurrentPlayer());
	}

	public void testTurnDone() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		RoundAboutPhase phase = new RoundAboutPhase(allPlayers, allPlayers.get(0));

		assertEquals(allPlayers.get(0), phase.getCurrentPlayer());
		phase.turnDone(0L);
		assertEquals(allPlayers.get(1), phase.getCurrentPlayer());
		phase.turnDone(1000L);
		assertEquals(allPlayers.get(2), phase.getCurrentPlayer());
		phase.turnDone(2000L);
		assertEquals(allPlayers.get(0), phase.getCurrentPlayer());
		assertEquals(3, phase.getAllPlayers().size());
	}
}
