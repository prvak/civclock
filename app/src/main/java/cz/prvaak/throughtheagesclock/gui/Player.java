package cz.prvaak.throughtheagesclock.gui;

import java.io.Serializable;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.clock.TimeAmountPerEpoch;

/**
 * Player clock specific to Through the Ages.
 */
public class Player extends PlayerClock implements Serializable {

	public final static int TIMES_PER_EPOCH = 3;

	public Player(PlayerColor playerColor, PlayerData data) {
		this(playerColor, data.baseTime, data.upkeepTime,
				getTimeAmountPerEpoch(data.turnBonusTimes));
	}

	public Player(PlayerColor playerColor, TimeAmount baseTime, TimeAmount upkeepTime,
				  TimeAmountPerEpoch turnBonusTimes) {
		super(playerColor, baseTime, upkeepTime, turnBonusTimes, new TimeAmount(30000L),
				new TimeAmount(10000L));
	}

	@Override
	public PlayerColor getPlayerId() {
		return (PlayerColor) super.getPlayerId();
	}

	public static TimeAmountPerEpoch getTimeAmountPerEpoch(TimeAmount[] timeAmounts) {
		if (timeAmounts.length != TIMES_PER_EPOCH) {
			throw new IllegalArgumentException(String.format(
					"There must be exactly %d time amounts, %d found instead!",
					TIMES_PER_EPOCH, timeAmounts.length));
		}

		TimeAmountPerEpoch timeAmountPerEpoch = new TimeAmountPerEpoch();
		timeAmountPerEpoch.put(Age.A, timeAmounts[0]);
		timeAmountPerEpoch.put(Age.I, timeAmounts[0]);
		timeAmountPerEpoch.put(Age.II, timeAmounts[1]);
		timeAmountPerEpoch.put(Age.III, timeAmounts[2]);
		timeAmountPerEpoch.put(Age.IV, timeAmounts[2]);

		return timeAmountPerEpoch;
	}


}
