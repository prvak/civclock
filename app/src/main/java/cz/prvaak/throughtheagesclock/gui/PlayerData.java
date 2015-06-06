package cz.prvaak.throughtheagesclock.gui;

import cz.prvaak.throughtheagesclock.TimeAmount;

/**
 * Data about single player.
 */
public class PlayerData {

	public TimeAmount baseTime;
	public TimeAmount turnBonusTime;
	public TimeAmount upkeepTime;

	public PlayerData(TimeAmount baseTime, TimeAmount turnBonusTime,
			TimeAmount upkeepTime) {
		this.baseTime = baseTime;
		this.turnBonusTime = turnBonusTime;
		this.upkeepTime = upkeepTime;
	}

	/** Create a copy of given PlayerData. */
	public PlayerData(PlayerData other) {
		this.baseTime = other.baseTime;
		this.turnBonusTime = other.turnBonusTime;
		this.upkeepTime = other.upkeepTime;
	}
}
