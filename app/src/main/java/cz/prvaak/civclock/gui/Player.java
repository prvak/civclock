package cz.prvaak.civclock.gui;

import java.io.Serializable;

import cz.prvaak.civclock.TimeAmount;
import cz.prvaak.civclock.clock.EpochTimeAmount;
import cz.prvaak.civclock.clock.PlayerClock;

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
				  EpochTimeAmount turnBonusTimes) {
		super(playerColor, baseTime, upkeepTime, turnBonusTimes, new TimeAmount(30000L),
				new TimeAmount(10000L));
	}

	@Override
	public PlayerColor getPlayerId() {
		return (PlayerColor) super.getPlayerId();
	}

	public static EpochTimeAmount getTimeAmountPerEpoch(TimeAmount[] timeAmounts) {
		if (timeAmounts.length != TIMES_PER_EPOCH) {
			throw new IllegalArgumentException(String.format(
					"There must be exactly %d time amounts, %d found instead!",
					TIMES_PER_EPOCH, timeAmounts.length));
		}

		EpochTimeAmount epochTimeAmount = new EpochTimeAmount();
		epochTimeAmount.put(Age.A, timeAmounts[0]);
		epochTimeAmount.put(Age.I, timeAmounts[0]);
		epochTimeAmount.put(Age.II, timeAmounts[1]);
		epochTimeAmount.put(Age.III, timeAmounts[2]);
		epochTimeAmount.put(Age.IV, timeAmounts[2]);

		return epochTimeAmount;
	}


}
