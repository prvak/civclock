package cz.prvaak.throughtheagesclock.phase;

import android.test.InstrumentationTestCase;

import java.util.List;

import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.FakePlayerClock;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.phase.OneOnOnePhase} class.
 */
public class OneOnOnePhaseTest extends InstrumentationTestCase {

	public void testFirstPlayer() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		FakePlayerClock player0 = (FakePlayerClock) allPlayers.get(0);
		FakePlayerClock player2 = (FakePlayerClock) allPlayers.get(2);
		OneOnOnePhase phase = new OneOnOnePhase(new TimeInstant(0L), player2, player0);

		assertEquals(player2, phase.getCurrentPlayer());
		assertEquals(1, phase.getNextPlayers().size());
		assertEquals(player0, phase.getNextPlayers().get(0));
	}

	public void testTurnDone() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		FakePlayerClock player0 = (FakePlayerClock) allPlayers.get(0);
		FakePlayerClock player2 = (FakePlayerClock) allPlayers.get(2);
		OneOnOnePhase phase = new OneOnOnePhase(new TimeInstant(0L), player2, player0);

		phase.turnDone(new TimeInstant(1000L));
		assertEquals(player0, phase.getCurrentPlayer());
		assertEquals(1, phase.getNextPlayers().size());
		assertEquals(player2, phase.getNextPlayers().get(0));
	}
}
