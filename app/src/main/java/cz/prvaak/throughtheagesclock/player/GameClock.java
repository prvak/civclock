package cz.prvaak.throughtheagesclock.player;

/**
 * High level game clock.
 */
public class GameClock {

	private final PlayerCountdown[] playerCountdowns;
	private int currentPlayer;
	private boolean isRunning;

	public GameClock(PlayerCountdown[] playerCountdowns) {
		this.playerCountdowns = playerCountdowns;
	}

	public void nextPlayer(long when) {
		if (isRunning) {
			PlayerCountdown currentPlayerCountdown = playerCountdowns[currentPlayer];
			int nextPlayer = (currentPlayer + 1) % playerCountdowns.length;
			PlayerCountdown nextPlayerCountdown = playerCountdowns[nextPlayer];

			currentPlayerCountdown.stop(when);
			currentPlayerCountdown.upkeep(when);
			nextPlayerCountdown.start(when);
			currentPlayer = nextPlayer;
		} else {
			PlayerCountdown currentPlayerCountdown = playerCountdowns[currentPlayer];

			currentPlayerCountdown.unstop(when);
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
