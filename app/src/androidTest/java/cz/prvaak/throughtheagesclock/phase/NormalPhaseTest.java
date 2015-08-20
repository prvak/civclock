package cz.prvaak.throughtheagesclock.phase;

import android.test.InstrumentationTestCase;

import java.util.List;

import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.FakeEpoch;
import cz.prvaak.throughtheagesclock.clock.FakePlayerClock;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.phase.NormalPhase} class.
 */
public class NormalPhaseTest extends InstrumentationTestCase {

	public void testFirstPlayer() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		NormalPhase phase = new NormalPhase(allPlayers, allPlayers.get(2));

		assertEquals(allPlayers.get(2), phase.getCurrentPlayer());
	}

	public void testTurnDone() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		NormalPhase phase = new NormalPhase(allPlayers, allPlayers.get(0));

		assertEquals(allPlayers.get(0), phase.getCurrentPlayer());
		phase.turnDone(new TimeInstant(0L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(1), phase.getCurrentPlayer());
		phase.turnDone(new TimeInstant(1000L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(2), phase.getCurrentPlayer());
		phase.turnDone(new TimeInstant(2000L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(0), phase.getCurrentPlayer());
		assertEquals(3, phase.getAllPlayers().size());
	}
}
