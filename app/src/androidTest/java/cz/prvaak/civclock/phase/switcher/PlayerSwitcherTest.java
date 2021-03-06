package cz.prvaak.civclock.phase.switcher;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

import cz.prvaak.civclock.TimeAmount;
import cz.prvaak.civclock.TimeInstant;
import cz.prvaak.civclock.clock.EpochId;
import cz.prvaak.civclock.clock.FakeEpoch;
import cz.prvaak.civclock.clock.FakePlayerClock;
import cz.prvaak.civclock.clock.FakePlayerId;
import cz.prvaak.civclock.clock.PlayerClock;
import cz.prvaak.civclock.phase.switcher.transition.PlayerTransition;

/**
 * Tests of {@link cz.prvaak.civclock.phase.switcher.PlayerSwitcher} class.
 */
public class PlayerSwitcherTest extends InstrumentationTestCase {

	public void testPlayerMustExist() {
		ArrayList<PlayerClock> playerClocks = FakePlayerClock.createPlayerClocks(3);
		try {
			new PlayerSwitcher(playerClocks, new PlayerClock(new FakePlayerId(),
					new TimeAmount(10000L), new TimeAmount(1000L),
					FakePlayerClock.createTimeAmountPerEpoch(),
					new TimeAmount(10000L), new TimeAmount(10000L)));
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

	public void testGetAllPlayers() {
		ArrayList<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(2);
		PlayerSwitcher playerSwitcher = new PlayerSwitcher(allPlayers, allPlayers.get(0));
		assertEquals(2, playerSwitcher.getAllPlayers().size());
	}

	public void testGetNextPlayers() {
		ArrayList<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		PlayerSwitcher playerSwitcher = new PlayerSwitcher(allPlayers, allPlayers.get(1));
		List<PlayerClock> nextPlayers = playerSwitcher.getNextPlayers();
		assertEquals(2, nextPlayers.size());
		assertEquals(allPlayers.get(2), nextPlayers.get(0));
		assertEquals(allPlayers.get(0), nextPlayers.get(1));
	}

	public void testGetNextPlayersAfterRemove() {
		ArrayList<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		PlayerSwitcher playerSwitcher = new PlayerSwitcher(allPlayers, allPlayers.get(1));
		FakePlayerTransition transition = new FakePlayerTransition();

		playerSwitcher.removeCurrentPlayer();
		playerSwitcher.switchPlayers(transition, new TimeInstant(2000L), FakeEpoch.ONE);
		List<PlayerClock> nextPlayers = playerSwitcher.getNextPlayers();
		assertEquals(1, nextPlayers.size());
		assertEquals(allPlayers.get(0), nextPlayers.get(0));
	}

	public void testRemoveDoesNotChangeInsertedList() {
		ArrayList<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		PlayerSwitcher playerSwitcher = new PlayerSwitcher(allPlayers, allPlayers.get(0));

		playerSwitcher.removeCurrentPlayer();
		assertEquals(2, playerSwitcher.getPlayersCount());
		assertEquals(2, playerSwitcher.getAllPlayers().size());
		assertEquals(3, allPlayers.size());
	}

	public void testSwitchPlayers() {
		ArrayList<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		PlayerSwitcher playerSwitcher = new PlayerSwitcher(allPlayers, allPlayers.get(0));
		FakePlayerTransition transition = new FakePlayerTransition();

		assertEquals(allPlayers.get(0), playerSwitcher.getCurrentPlayer());

		playerSwitcher.switchPlayers(transition, new TimeInstant(0L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(1), playerSwitcher.getCurrentPlayer());
		assertEquals(allPlayers.get(0), transition.playerBeforeSwitch);
		assertEquals(allPlayers.get(1), transition.playerAfterSwitch);

		playerSwitcher.switchPlayers(transition, new TimeInstant(1000L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(2), playerSwitcher.getCurrentPlayer());
		assertEquals(allPlayers.get(1), transition.playerBeforeSwitch);
		assertEquals(allPlayers.get(2), transition.playerAfterSwitch);

		playerSwitcher.switchPlayers(transition, new TimeInstant(2000L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(0), playerSwitcher.getCurrentPlayer());
		assertEquals(allPlayers.get(2), transition.playerBeforeSwitch);
		assertEquals(allPlayers.get(0), transition.playerAfterSwitch);
	}

	/** Transition that does nothing. */
	private static class FakePlayerTransition implements PlayerTransition {
		public PlayerClock playerBeforeSwitch;
		public PlayerClock playerAfterSwitch;
		private TimeInstant switchTime;

		@Override
		public void beforeSwitch(PlayerClock activePlayer, TimeInstant when, EpochId epoch) {
			playerBeforeSwitch = activePlayer;
			switchTime = when;

		}

		@Override
		public void afterSwitch(PlayerClock activePlayer, TimeInstant when, EpochId epoch) {
			playerAfterSwitch = activePlayer;
			assertEquals(switchTime, when);
		}
	}
}
