package cz.prvaak.throughtheagesclock.gui;

import cz.prvaak.throughtheagesclock.TimeAmount;

/**
 * Data about single player.
 */
public class PlayerData {

	public final TimeAmount baseTime;
	public final TimeAmount turnBonusTime;
	public final TimeAmount upkeepTime;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		PlayerData that = (PlayerData) o;

		if (!baseTime.equals(that.baseTime)) {
			return false;
		} else if (!turnBonusTime.equals(that.turnBonusTime)) {
			return false;
		} else if (!upkeepTime.equals(that.upkeepTime)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = baseTime != null ? baseTime.hashCode() : 0;
		result = 31 * result + (turnBonusTime != null ? turnBonusTime.hashCode() : 0);
		result = 31 * result + (upkeepTime != null ? upkeepTime.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return String.format("%s + %s (+ %s)", baseTime, turnBonusTime, upkeepTime);
	}
}
