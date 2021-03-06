package cz.prvaak.civclock.phase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.prvaak.civclock.TimeInstant;
import cz.prvaak.civclock.clock.EpochId;
import cz.prvaak.civclock.clock.PlayerClock;
import cz.prvaak.civclock.phase.switcher.PlayerSwitcher;
import cz.prvaak.civclock.phase.switcher.transition.FinalAuctionTransition;
import cz.prvaak.civclock.phase.switcher.transition.NormalTransition;
import cz.prvaak.civclock.phase.switcher.transition.PlayerTransition;

/**
 * Phase when players are auctioning. When a player passes he no longer participates in the auction.
 */
public class AuctionPhase implements GamePhase {

	private final PlayerSwitcher playerSwitcher;
	private final PlayerTransition transition = new NormalTransition();
	private final PlayerTransition finalTransition;
	private final PlayerClock initiatingPlayer;

	public AuctionPhase(List<? extends PlayerClock> allPlayers, PlayerClock currentPlayer) {
		this.playerSwitcher = new PlayerSwitcher(allPlayers, currentPlayer);
		this.finalTransition = new FinalAuctionTransition(currentPlayer);
		this.initiatingPlayer = currentPlayer;
	}

	/**
	 * Terminate turn of current player and keep him between active players.
	 *
	 * @param when Time in milliseconds when the bid was done.
	 * @param epoch Current epoch.
	 */
	public void bid(TimeInstant when, EpochId epoch) {
		checkThatAuctionIsNotOver();
		playerSwitcher.switchPlayers(transition, when, epoch);
	}

	/**
	 * Terminate turn of current player and remove him from active players.
	 * Current player will no longer participate in current phase.
	 *
	 * @param when Time in milliseconds when the pass was done.
	 * @param epoch Current epoch.
	 */
	public void pass(TimeInstant when, EpochId epoch) {
		checkThatAuctionIsNotOver();
		playerSwitcher.removeCurrentPlayer();
		if (isAuctionOver()) {
			playerSwitcher.switchPlayers(finalTransition, when, epoch);
		} else {
			playerSwitcher.switchPlayers(transition, when, epoch);
		}
	}

	@Override
	public List<PlayerClock> getAllPlayers() {
		if (isAuctionOver()) {
			return Collections.emptyList();
		} else {
			return playerSwitcher.getAllPlayers();
		}
	}

	@Override
	public List<PlayerClock> getNextPlayers() {
		if (isAuctionOver()) {
			ArrayList<PlayerClock> list = new ArrayList<>(1);
			list.add(initiatingPlayer);
			return list;
		} else {
			return playerSwitcher.getNextPlayers();
		}
	}

	@Override
	public PlayerClock getCurrentPlayer() {
		if (isAuctionOver()) {
			return initiatingPlayer;
		} else {
			return playerSwitcher.getCurrentPlayer();
		}
	}

	private boolean isAuctionOver() {
		return playerSwitcher.getPlayersCount() <= 1;
	}

	private void checkThatAuctionIsNotOver() {
		if (isAuctionOver()) {
			throw new IllegalArgumentException("Auction is over!");
		}
	}
}
