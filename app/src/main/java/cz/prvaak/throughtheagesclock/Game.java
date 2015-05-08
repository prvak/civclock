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
	private final List<PlayerClock> remainingPlayers;

	public Game(List<PlayerClock> allPlayers) {
		this.remainingPlayers = allPlayers;
	}

	public RoundAboutPhase startGame(long when) {
		PlayerClock firstPlayer = remainingPlayers.get(0);
		firstPlayer.start(when);
		return new RoundAboutPhase(remainingPlayers, firstPlayer);
	}

	public RoundAboutPhase startRoundAboutPhase(Phase previousPhase) {
		return new RoundAboutPhase(remainingPlayers, previousPhase.getCurrentPlayer());
	}

	public AuctionPhase startAuctionPhase(Phase previousPhase) {
		return new AuctionPhase(remainingPlayers, previousPhase.getCurrentPlayer());
	}

}
