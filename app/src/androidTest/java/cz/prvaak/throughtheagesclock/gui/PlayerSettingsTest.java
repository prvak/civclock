package cz.prvaak.throughtheagesclock.gui;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.Iterator;

import cz.prvaak.throughtheagesclock.TimeAmount;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.gui.PlayerSettings} class.
 */
public class PlayerSettingsTest extends InstrumentationTestCase {

	/** Create settings with blue, red and yellow players. */
	private PlayerSettings createPlayerSettings() {
		return new PlayerSettings(PlayerColor.BLUE, PlayerColor.RED, PlayerColor.YELLOW);
	}

	/** Create player data that differ for each player. */
	private PlayerData createPlayerData(PlayerColor playerColor) {
		long baseTimeMs = 0L;
		switch (playerColor) {
			case RED:
				baseTimeMs = 1000L;
				break;
			case GREEN:
				baseTimeMs = 2000L;
				break;
			case BLUE:
				baseTimeMs = 3000L;
				break;
			case YELLOW:
				baseTimeMs = 4000L;
				break;
		}
		return createPlayerData(baseTimeMs);
	}

	/** Create player data with given base time. */
	private PlayerData createPlayerData(long baseTimeMs) {
		TimeAmount baseTime = new TimeAmount(baseTimeMs);
		TimeAmount turnBonusTime = new TimeAmount(30000L);
		TimeAmount upkeepTime = new TimeAmount(10000L);
		return new PlayerData(baseTime, turnBonusTime, upkeepTime);
	}

	public void testMinimumPlayers() throws Exception {
		try {
			new PlayerSettings(1); // Too few players.
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testMaximumPlayers() throws Exception {
		try {
			new PlayerSettings(5); // Too many players.
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testConstructorForTwoPlayers() throws Exception {
		PlayerSettings playerSettings = new PlayerSettings(PlayerColor.RED, PlayerColor.YELLOW);
		Iterator<PlayerColor> iterator = playerSettings.iterator();
		assertEquals(2, playerSettings.getPlayerCount());
		assertEquals(PlayerColor.RED, iterator.next());
		assertEquals(PlayerColor.YELLOW, iterator.next());
	}

	public void testConstructorForThreePlayers() throws Exception {
		PlayerSettings playerSettings = new PlayerSettings(PlayerColor.GREEN, PlayerColor.RED,
				PlayerColor.YELLOW);
		Iterator<PlayerColor> iterator = playerSettings.iterator();
		assertEquals(3, playerSettings.getPlayerCount());
		assertEquals(PlayerColor.GREEN, iterator.next());
		assertEquals(PlayerColor.RED, iterator.next());
		assertEquals(PlayerColor.YELLOW, iterator.next());
	}

	public void testConstructorForFourPlayers() throws Exception {
		PlayerSettings playerSettings = new PlayerSettings(PlayerColor.BLUE, PlayerColor.RED,
				PlayerColor.GREEN, PlayerColor.YELLOW);
		Iterator<PlayerColor> iterator = playerSettings.iterator();
		assertEquals(4, playerSettings.getPlayerCount());
		assertEquals(PlayerColor.BLUE, iterator.next());
		assertEquals(PlayerColor.RED, iterator.next());
		assertEquals(PlayerColor.GREEN, iterator.next());
		assertEquals(PlayerColor.YELLOW, iterator.next());
	}

	public void testAddPlayer() throws Exception {
		PlayerSettings playerSettings = createPlayerSettings();
		assertEquals(3, playerSettings.getPlayerCount());
		playerSettings.addPlayer();
		assertEquals(4, playerSettings.getPlayerCount());

		Iterator<PlayerColor> colors = playerSettings.iterator();
		assertEquals(PlayerColor.BLUE, colors.next());
		assertEquals(PlayerColor.RED, colors.next());
		assertEquals(PlayerColor.YELLOW, colors.next());
		assertEquals(PlayerColor.GREEN, colors.next());
	}

	public void testAddTooManyPlayers() throws Exception {
		PlayerSettings playerSettings = createPlayerSettings();
		assertEquals(3, playerSettings.getPlayerCount());
		assertTrue(playerSettings.addPlayer());
		assertFalse(playerSettings.addPlayer());
		assertEquals(4, playerSettings.getPlayerCount());
	}

	public void testRemovePlayer() throws Exception {
		PlayerSettings playerSettings = createPlayerSettings();
		assertTrue(playerSettings.removePlayer(PlayerColor.RED));
		assertEquals(2, playerSettings.getPlayerCount());
		assertFalse(playerSettings.isPlayerActive(PlayerColor.RED));

		Iterator<PlayerColor> colors = playerSettings.iterator();
		assertEquals(PlayerColor.BLUE, colors.next());
		assertEquals(PlayerColor.YELLOW, colors.next());
	}

	public void testRemoveTooManyPlayers() throws Exception {
		PlayerSettings playerSettings = createPlayerSettings();
		assertEquals(3, playerSettings.getPlayerCount());
		assertTrue(playerSettings.removePlayer(PlayerColor.RED));
		assertFalse(playerSettings.removePlayer(PlayerColor.GREEN));
		assertEquals(2, playerSettings.getPlayerCount());
	}

	public void testAllPlayersHaveSameData() throws Exception {
		PlayerSettings playerSettings = createPlayerSettings();
		PlayerData blueData = playerSettings.getPlayerData(PlayerColor.BLUE);
		PlayerData redData = playerSettings.getPlayerData(PlayerColor.RED);
		PlayerData yellowData = playerSettings.getPlayerData(PlayerColor.YELLOW);

		assertEquals(blueData, redData);
		assertEquals(redData, yellowData);
	}

	public void testChangeData() throws Exception {
		PlayerSettings playerSettings = createPlayerSettings();
		PlayerData newData = createPlayerData(11000L);
		PlayerData blueData = playerSettings.getPlayerData(PlayerColor.BLUE);
		PlayerData redData = playerSettings.getPlayerData(PlayerColor.RED);
		PlayerData yellowData = playerSettings.getPlayerData(PlayerColor.YELLOW);

		assertNotSame(newData, blueData);
		assertNotSame(newData, redData);
		assertNotSame(newData, yellowData);

		playerSettings.changeData(PlayerColor.RED, newData);

		assertEquals(blueData, playerSettings.getPlayerData(PlayerColor.BLUE));
		assertEquals(newData, playerSettings.getPlayerData(PlayerColor.RED));
		assertEquals(yellowData, playerSettings.getPlayerData(PlayerColor.YELLOW));
	}

	public void testChangeActiveColorToActiveColor() throws Exception {
		PlayerSettings playerSettings = createPlayerSettings();
		PlayerData blueData = createPlayerData(PlayerColor.BLUE);
		PlayerData yellowData = createPlayerData(PlayerColor.YELLOW);
		playerSettings.changeData(PlayerColor.BLUE, blueData);
		playerSettings.changeData(PlayerColor.YELLOW, yellowData);

		playerSettings.changeColor(PlayerColor.BLUE, PlayerColor.YELLOW);

		// Check that the player retains its original data when its color is changed.
		assertEquals(blueData, playerSettings.getPlayerData(PlayerColor.YELLOW));
		assertEquals(yellowData, playerSettings.getPlayerData(PlayerColor.BLUE));

		// Check that there are still three players.
		assertEquals(3, playerSettings.getPlayerCount());

		// Check that the order of players is changed.
		Iterator<PlayerColor> colors = playerSettings.iterator();
		assertEquals(PlayerColor.YELLOW, colors.next());
		assertEquals(PlayerColor.RED, colors.next());
		assertEquals(PlayerColor.BLUE, colors.next());
	}

	public void testChangeActiveColorToInactiveColor() throws Exception {
		PlayerSettings playerSettings = createPlayerSettings();

		// Change player data so we can check that changing color does not change the data.
		PlayerData blueData = createPlayerData(PlayerColor.BLUE);
		playerSettings.changeData(PlayerColor.BLUE, blueData);
		playerSettings.changeColor(PlayerColor.BLUE, PlayerColor.GREEN);

		// Check that the player retains its original data when its color is changed.
		assertEquals(blueData, playerSettings.getPlayerData(PlayerColor.GREEN));

		// Check that there are still three players.
		assertEquals(3, playerSettings.getPlayerCount());

		// Check that the order of players is changed.
		Iterator<PlayerColor> colors = playerSettings.iterator();
		assertEquals(PlayerColor.GREEN, colors.next());
		assertEquals(PlayerColor.RED, colors.next());
		assertEquals(PlayerColor.YELLOW, colors.next());

		// Check that the original color is now inactive.
		assertFalse(playerSettings.isPlayerActive(PlayerColor.BLUE));
	}

	public void testChangeInactiveColorToActiveColor() throws Exception {
		PlayerSettings playerSettings = createPlayerSettings();

		// Change player data so we can check that changing color does not change the data.
		PlayerData blueData = createPlayerData(PlayerColor.BLUE);
		playerSettings.changeData(PlayerColor.BLUE, blueData);
		playerSettings.changeColor(PlayerColor.GREEN, PlayerColor.BLUE);

		// Check that the player retains its original data when its color is changed.
		assertEquals(blueData, playerSettings.getPlayerData(PlayerColor.GREEN));

		// Check that there are still three players.
		assertEquals(3, playerSettings.getPlayerCount());

		// Check that the order of players is changed.
		Iterator<PlayerColor> colors = playerSettings.iterator();
		assertEquals(PlayerColor.GREEN, colors.next());
		assertEquals(PlayerColor.RED, colors.next());
		assertEquals(PlayerColor.YELLOW, colors.next());

		// Check that the original color is now inactive.
		assertFalse(playerSettings.isPlayerActive(PlayerColor.BLUE));
	}

	public void testChangeInactiveColorToInactiveColor() throws Exception {
		PlayerSettings playerSettings = new PlayerSettings(PlayerColor.RED, PlayerColor.GREEN);
		try {
			playerSettings.changeColor(PlayerColor.BLUE, PlayerColor.YELLOW);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
