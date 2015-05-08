package cz.prvaak.throughtheagesclock;

import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Phase when players are auctioning. When a player passes he no longer participates in the auction.
 */
public class AuctionPhase implements Phase {

	private final PlayersOrder playersOrder;

	public AuctionPhase(PlayersOrder playersOrder) {
		this.playersOrder = playersOrder;
	}

	public void bid(long when) {
		checkThatAuctionIsNotOver();
		playersOrder.switchPlayers(when);
	}

	public void pass(long when) {
		checkThatAuctionIsNotOver();
		playersOrder.removeCurrentPlayer();
		playersOrder.switchPlayers(when);
	}

	@Override
	public List<PlayerClock> getRemainingPlayers() {
		return playersOrder.getRemainingPlayers();
	}

	@Override
	public PlayerClock getCurrentPlayer() {
		return playersOrder.getCurrentPlayer();
	}

	private void checkThatAuctionIsNotOver() {
		if (playersOrder.getPlayersCount() <= 1) {
			throw new IllegalArgumentException("Auction is over!");
		}
	}
}
