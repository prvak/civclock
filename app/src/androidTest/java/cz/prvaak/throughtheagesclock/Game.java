package cz.prvaak.throughtheagesclock;

import cz.prvaak.throughtheagesclock.player.PlayerCountdown;

/**
 * Created by michal on 3/1/15.
 */
public class Game {
	private enum State {NORMAL, NEGOTIATION, OFFER};
	private final PlayerCountdown[] playerCountdowns;
	private State state = State.NORMAL;
	private boolean isRunning;

	public Game(PlayerCountdown[] playerCountdowns) {
		this.playerCountdowns = playerCountdowns;
	}


}
