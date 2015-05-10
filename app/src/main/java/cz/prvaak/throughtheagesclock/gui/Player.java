package cz.prvaak.throughtheagesclock.gui;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.clock.PlayerId;

/**
 * Created by michal on 5/10/15.
 */
public class Player extends PlayerClock {

	public Player(PlayerColor playerColor, long baseTime, long upkeepTime) {
		super(playerColor, baseTime, upkeepTime);
	}

	public PlayerColor getPlayerColor() {
		return getPlayerId();
	}

	@Override
	public PlayerColor getPlayerId() {
		return (PlayerColor) super.getPlayerId();
	}
}
