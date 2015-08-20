package cz.prvaak.throughtheagesclock.gui;

import java.util.Arrays;

import cz.prvaak.throughtheagesclock.TimeAmount;

/**
 * Data about single player.
 */
public class PlayerData {

	public final TimeAmount baseTime;
	public final TimeAmount[] turnBonusTimes;
	public final TimeAmount upkeepTime;

	public PlayerData(TimeAmount baseTime, TimeAmount[] turnBonusTimes,
			TimeAmount upkeepTime) {
		this.baseTime = baseTime;
		this.turnBonusTimes = turnBonusTimes;
		this.upkeepTime = upkeepTime;
	}

	/** Create a copy of given PlayerData. */
	public PlayerData(PlayerData other) {
		this.baseTime = other.baseTime;
		this.turnBonusTimes = new TimeAmount[other.turnBonusTimes.length];
		System.arraycopy(other.turnBonusTimes, 0, this.turnBonusTimes, 0,
				other.turnBonusTimes.length);
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
		} else if (!Arrays.equals(turnBonusTimes, that.turnBonusTimes)) {
			return false;
		} else if (!upkeepTime.equals(that.upkeepTime)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = baseTime != null ? baseTime.hashCode() : 0;
		result = 31 * result + (turnBonusTimes != null ? Arrays.hashCode(turnBonusTimes) : 0);
		result = 31 * result + (upkeepTime != null ? upkeepTime.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(baseTime);
		builder.append("+");
		for (TimeAmount timeAmount: turnBonusTimes) {
			builder.append(timeAmount);
			builder.append("+");
		}
		builder.append("(");
		builder.append(upkeepTime);
		builder.append(")");
		return builder.toString();
	}
}
