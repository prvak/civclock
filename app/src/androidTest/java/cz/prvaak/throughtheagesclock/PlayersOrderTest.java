package cz.prvaak.throughtheagesclock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.PlayersOrder} class.
 */
public class PlayersOrderTest extends InstrumentationTestCase {

	private ArrayList<PlayerClock> createPlayerClocks(int howMany) {
		ArrayList<PlayerClock> playerClocks = new ArrayList<>(howMany);
		for (int i = 0; i < howMany; i++) {
			playerClocks.add(new PlayerClock(10000, 1000));
		}
		playerClocks.get(0).start(0L);
		return playerClocks;
	}

	public void testPlayerMustExist() {
		ArrayList<PlayerClock> playerClocks = createPlayerClocks(3);
		try {
			new PlayersOrder(playerClocks, new PlayerClock(10000, 1000));
			Assert.fail("Should have thrown IllegalArgumentException!");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testGetPlayersCount() {
		ArrayList<PlayerClock> allPlayers = createPlayerClocks(3);
		PlayersOrder playersOrder = new PlayersOrder(allPlayers, allPlayers.get(0));
		assertEquals(allPlayers.size(), playersOrder.getPlayersCount());
	}

	public void testGetRemainingPlayers() {
		ArrayList<PlayerClock> allPlayers = createPlayerClocks(2);
		PlayersOrder playersOrder = new PlayersOrder(allPlayers, allPlayers.get(0));
		List<PlayerClock> remainingPlayers = playersOrder.getRemainingPlayers();
		assertEquals(2, remainingPlayers.size());
	}

	public void testRemoveDoesNotChangeInsertedList() {
		ArrayList<PlayerClock> allPlayers = createPlayerClocks(3);
		PlayersOrder playersOrder = new PlayersOrder(allPlayers, allPlayers.get(0));
		playersOrder.removeCurrentPlayer();
		assertEquals(2, playersOrder.getPlayersCount());
		assertEquals(2, playersOrder.getRemainingPlayers().size());
		assertEquals(3, allPlayers.size());
	}

	public void testSwitchPlayers() {
		ArrayList<PlayerClock> allPlayers = createPlayerClocks(3);
		PlayersOrder playersOrder = new PlayersOrder(allPlayers, allPlayers.get(0));
		assertEquals(allPlayers.get(0), playersOrder.getCurrentPlayer());
		playersOrder.switchPlayers(0L);
		assertEquals(allPlayers.get(1), playersOrder.getCurrentPlayer());
		playersOrder.switchPlayers(1000L);
		assertEquals(allPlayers.get(2), playersOrder.getCurrentPlayer());
		playersOrder.switchPlayers(2000L);
		assertEquals(allPlayers.get(0), playersOrder.getCurrentPlayer());
	}
}
