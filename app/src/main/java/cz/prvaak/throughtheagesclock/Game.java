package cz.prvaak.throughtheagesclock;

import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.phase.AuctionPhase;
import cz.prvaak.throughtheagesclock.phase.Phase;
import cz.prvaak.throughtheagesclock.phase.RoundAboutPhase;

/**
 * High level game controls.
 */
public class Game {
	private final List<? extends PlayerClock> remainingPlayers;
	private boolean isPaused;

	public Game(List<? extends PlayerClock> allPlayers) {
		this.remainingPlayers = allPlayers;
	}

	public RoundAboutPhase startGame(long when) {
		PlayerClock firstPlayer = remainingPlayers.get(0);
		firstPlayer.start(when);
		return new RoundAboutPhase(remainingPlayers, firstPlayer);
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

	public RoundAboutPhase startRoundAboutPhase(Phase previousPhase) {
		return new RoundAboutPhase(remainingPlayers, previousPhase.getCurrentPlayer());
	}

	public AuctionPhase startAuctionPhase(Phase previousPhase) {
		return new AuctionPhase(remainingPlayers, previousPhase.getCurrentPlayer());
	}

}
