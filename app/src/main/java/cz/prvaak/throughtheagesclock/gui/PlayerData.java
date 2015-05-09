package cz.prvaak.throughtheagesclock.gui;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Data about single player.
 */
public class PlayerData {

	private PlayerClock playerClock;
	private PlayerColor playerColor;

	public PlayerData(PlayerClock playerClock, PlayerColor playerColor) {
		this.playerClock = playerClock;
		this.playerColor = playerColor;
	}

	public PlayerClock getPlayerClock() {
		return playerClock;
	}

	public PlayerColor getPlayerColor() {
		return playerColor;
	}
}
