package cz.prvaak.throughtheagesclock.gui;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Player clock specific to Through the Ages.
 */
public class Player extends PlayerClock {

	public Player(PlayerData data) {
		this(data.playerColor, data.baseTime, data.upkeepTime, data.turnBonusTime);
	}

	public Player(PlayerColor playerColor, long baseTime, long upkeepTime, long turnBonusTime) {
		super(playerColor, baseTime, upkeepTime, turnBonusTime);
	}

	public PlayerColor getPlayerColor() {
		return getPlayerId();
	}

	@Override
	public PlayerColor getPlayerId() {
		return (PlayerColor) super.getPlayerId();
	}
}
