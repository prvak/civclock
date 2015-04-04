package cz.prvaak.throughtheagesclock.player;

import cz.prvaak.throughtheagesclock.player.PlayerClock;

/**
 * High level game clock.
 */
public class GameClock {

	private final PlayerClock[] playerClocks;
	private int currentPlayer;
	private boolean isRunning;

	public GameClock(PlayerClock[] playerClocks) {
		this.playerClocks = playerClocks;
	}

	public void nextPlayer(long when) {
		if (isRunning) {
			PlayerClock currentPlayerClock = playerClocks[currentPlayer];
			int nextPlayer = (currentPlayer + 1) % playerClocks.length;
			PlayerClock nextPlayerClock = playerClocks[nextPlayer];

			currentPlayerClock.stop(when);
			currentPlayerClock.upkeep(when);
			nextPlayerClock.start(when);
			currentPlayer = nextPlayer;
		} else {
			PlayerClock currentPlayerClock = playerClocks[currentPlayer];

			currentPlayerClock.unstop(when);
		}
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public long getRemainingTime(long when, int player) {
		return 0L;
	}

	public long getRemainingUpkeepTime(long when, int player) {
		return 0L;
	}
}
