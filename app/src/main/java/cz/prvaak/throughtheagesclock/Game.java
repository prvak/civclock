package cz.prvaak.throughtheagesclock;

import java.io.Serializable;
import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.phase.AuctionPhase;
import cz.prvaak.throughtheagesclock.phase.GamePhase;
import cz.prvaak.throughtheagesclock.phase.NormalPhase;
import cz.prvaak.throughtheagesclock.phase.OneOnOnePhase;

/**
 * High level game controls.
 */
public class Game implements Serializable {
	private final List<? extends PlayerClock> remainingPlayers;
	private GamePhase currentPhase;
	private boolean isPaused;

	public Game(List<? extends PlayerClock> allPlayers) {
		this.remainingPlayers = allPlayers;
	}

	public void start(long when) {
		PlayerClock firstPlayer = remainingPlayers.get(0);
		firstPlayer.start(when);
		currentPhase = new NormalPhase(remainingPlayers, firstPlayer);
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
		currentPhase = new NormalPhase(remainingPlayers, currentPhase.getCurrentPlayer());
	}

	public void startAuctionPhase(long when) {
		currentPhase = new AuctionPhase(remainingPlayers, currentPhase.getCurrentPlayer());
	}

	public void startOneOnOnePhase(long when, PlayerClock targetPlayer) {
		currentPhase = new OneOnOnePhase(when, targetPlayer, currentPhase.getCurrentPlayer());
	}

	public GamePhase getCurrentPhase() {
		return currentPhase;
	}
}
