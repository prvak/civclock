package cz.prvaak.civclock.phase;

import android.test.InstrumentationTestCase;

import java.util.List;

import cz.prvaak.civclock.TimeInstant;
import cz.prvaak.civclock.clock.FakeEpoch;
import cz.prvaak.civclock.clock.FakePlayerClock;
import cz.prvaak.civclock.clock.PlayerClock;
import cz.prvaak.civclock.clock.PlayerId;

/**
 * Tests of {@link cz.prvaak.civclock.phase.NormalPhase} class.
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

	public void testResign() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		NormalPhase phase = new NormalPhase(allPlayers, allPlayers.get(0));
		PlayerId playerId = phase.getCurrentPlayer().getPlayerId();

		phase.resign(new TimeInstant(0L), FakeEpoch.ONE);
		assertEquals(2, phase.getAllPlayers().size());
		assertNotSame(playerId, phase.getCurrentPlayer().getPlayerId());

		phase.turnDone(new TimeInstant(1000L), FakeEpoch.ONE);
		assertNotSame(playerId, phase.getCurrentPlayer().getPlayerId());

		phase.turnDone(new TimeInstant(2000L), FakeEpoch.ONE);
		assertNotSame(playerId, phase.getCurrentPlayer().getPlayerId());
	}
}
