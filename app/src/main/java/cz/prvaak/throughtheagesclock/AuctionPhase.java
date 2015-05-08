package cz.prvaak.throughtheagesclock;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Phase when players are auctioning. When a player passes he no longer participates in the auction.
 */
public class AuctionPhase implements Phase {

	private final List<PlayerClock> playerClocks;
	private Iterator<PlayerClock> iterator;
	private PlayerClock currentPlayer;

	public AuctionPhase(List<PlayerClock> playerClocks, PlayerClock currentPlayer) {
		if (playerClocks.size() <= 1) {
			throw new IllegalArgumentException("Not enough players!");
		}

		this.playerClocks = new LinkedList<>(playerClocks);
		this.iterator = this.playerClocks.listIterator(playerClocks.indexOf(currentPlayer));
		this.currentPlayer = getNextPlayer();
	}

	public void bid(long when) {
		checkThatAuctionIsNotOver();
		switchPlayers(when);
	}

	public void pass(long when) {
		checkThatAuctionIsNotOver();
		removeCurrentPlayer();
		switchPlayers(when);
	}

	@Override
	public List<PlayerClock> getAllPlayers() {
		return Collections.unmodifiableList(playerClocks);
	}

	@Override
	public PlayerClock getCurrentPlayer() {
		return currentPlayer;
	}

	private void checkThatAuctionIsNotOver() {
		if (playerClocks.size() <= 1) {
			throw new IllegalArgumentException("Auction is over!");
		}
	}

	private void removeCurrentPlayer() {
		iterator.remove();
	}

	private void switchPlayers(long when) {
		PlayerClock nextPlayer = getNextPlayer();
		currentPlayer.stop(when);
		nextPlayer.start(when);
		currentPlayer = nextPlayer;
	}

	private PlayerClock getNextPlayer() {
		PlayerClock nextPlayer = iterator.next();
		if (!iterator.hasNext()) {
			iterator = playerClocks.listIterator();
		}
		return nextPlayer;
	}
}
