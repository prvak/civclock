package cz.prvaak.throughtheagesclock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Tests of {@link PlayerSwitcher} class.
 */
public class PlayerSwitcherTest extends InstrumentationTestCase {

	public void testPlayerMustExist() {
		ArrayList<PlayerClock> playerClocks = FakePlayerClock.createPlayerClocks(3);
		try {
			new PlayerSwitcher(playerClocks, new PlayerClock(10000, 1000));
			Assert.fail("Should have thrown IllegalArgumentException!");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testGetPlayersCount() {
		ArrayList<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		PlayerSwitcher playerSwitcher = new PlayerSwitcher(allPlayers, allPlayers.get(0));
		assertEquals(allPlayers.size(), playerSwitcher.getPlayersCount());
	}

	public void testGetRemainingPlayers() {
		ArrayList<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(2);
		PlayerSwitcher playerSwitcher = new PlayerSwitcher(allPlayers, allPlayers.get(0));
		List<PlayerClock> remainingPlayers = playerSwitcher.getRemainingPlayers();
		assertEquals(2, remainingPlayers.size());
	}

	public void testRemoveDoesNotChangeInsertedList() {
		ArrayList<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		PlayerSwitcher playerSwitcher = new PlayerSwitcher(allPlayers, allPlayers.get(0));
		playerSwitcher.removeCurrentPlayer();
		assertEquals(2, playerSwitcher.getPlayersCount());
		assertEquals(2, playerSwitcher.getRemainingPlayers().size());
		assertEquals(3, allPlayers.size());
	}

	public void testSwitchPlayers() {
		ArrayList<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		PlayerSwitcher playerSwitcher = new PlayerSwitcher(allPlayers, allPlayers.get(0));
		FakePlayerTransition transition = new FakePlayerTransition();

		assertEquals(allPlayers.get(0), playerSwitcher.getCurrentPlayer());

		playerSwitcher.switchPlayers(transition, 0L);
		assertEquals(allPlayers.get(1), playerSwitcher.getCurrentPlayer());
		assertEquals(allPlayers.get(0), transition.playerBeforeSwitch);
		assertEquals(allPlayers.get(1), transition.playerAfterSwitch);

		playerSwitcher.switchPlayers(transition, 1000L);
		assertEquals(allPlayers.get(2), playerSwitcher.getCurrentPlayer());
		assertEquals(allPlayers.get(1), transition.playerBeforeSwitch);
		assertEquals(allPlayers.get(2), transition.playerAfterSwitch);

		playerSwitcher.switchPlayers(transition, 2000L);
		assertEquals(allPlayers.get(0), playerSwitcher.getCurrentPlayer());
		assertEquals(allPlayers.get(2), transition.playerBeforeSwitch);
		assertEquals(allPlayers.get(0), transition.playerAfterSwitch);
	}

	/** Transition that does nothing. */
	private static class FakePlayerTransition implements PlayerTransition {
		public PlayerClock playerBeforeSwitch;
		public PlayerClock playerAfterSwitch;
		private long switchTime;

		@Override
		public void beforeSwitch(PlayerClock activePlayer, long when) {
			playerBeforeSwitch = activePlayer;
			switchTime = when;

		}

		@Override
		public void afterSwitch(PlayerClock activePlayer, long when) {
			playerAfterSwitch = activePlayer;
			assertEquals(switchTime, when);
		}
	}
}
