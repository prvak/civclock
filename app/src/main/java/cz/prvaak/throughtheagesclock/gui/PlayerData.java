package cz.prvaak.throughtheagesclock.gui;

import cz.prvaak.throughtheagesclock.TimeAmount;

/**
 * Data about single player.
 */
public class PlayerData {

	public PlayerColor playerColor;
	public TimeAmount baseTime;
	public TimeAmount turnBonusTime;
	public TimeAmount upkeepTime;

	public PlayerData(PlayerColor playerColor, TimeAmount baseTime, TimeAmount turnBonusTime,
			TimeAmount upkeepTime) {
		this.playerColor = playerColor;
		this.baseTime = baseTime;
		this.turnBonusTime = turnBonusTime;
		this.upkeepTime = upkeepTime;
	}
}
