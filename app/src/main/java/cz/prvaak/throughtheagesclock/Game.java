package cz.prvaak.throughtheagesclock;

import java.io.Serializable;
import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.phase.AuctionPhase;
import cz.prvaak.throughtheagesclock.phase.Phase;
import cz.prvaak.throughtheagesclock.phase.RoundAboutPhase;

/**
 * High level game controls.
 */
public class Game implements Serializable {
	private final List<? extends PlayerClock> remainingPlayers;
	private Phase currentPhase;
	private boolean isPaused;

	public Game(List<? extends PlayerClock> allPlayers) {
		this.remainingPlayers = allPlayers;
	}

	public void start(long when) {
		PlayerClock firstPlayer = remainingPlayers.get(0);
		firstPlayer.start(when);
		currentPhase = new RoundAboutPhase(remainingPlayers, firstPlayer);
	}

	public void pause(long when) {
		for (PlayerClock playerClock: remainingPlayers) {
			playerClock.pause(when);
		}
		isPaused = true;
	}

	public void resume(long when) {
		for (PlayerClock playerClock: remainingPlayers) {
			playerClock.resume(when);
		}
		isPaused = false;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void startRoundAboutPhase(long when) {
		currentPhase = new RoundAboutPhase(remainingPlayers, currentPhase.getCurrentPlayer());
	}

	public void startAuctionPhase(long when) {
		currentPhase = new AuctionPhase(remainingPlayers, currentPhase.getCurrentPlayer());
	}

	public Phase getCurrentPhase() {
		return currentPhase;
	}
}
