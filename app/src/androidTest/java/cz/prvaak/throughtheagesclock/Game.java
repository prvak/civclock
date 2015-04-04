package cz.prvaak.throughtheagesclock;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Created by michal on 3/1/15.
 */
public class Game {
	private enum State {NORMAL, NEGOTIATION, OFFER};
	private final PlayerClock[] playerClocks;
	private State state = State.NORMAL;
	private boolean isRunning;

	public Game(PlayerClock[] playerClocks) {
		this.playerClocks = playerClocks;
	}


}
