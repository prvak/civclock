package cz.prvaak.throughtheagesclock;

import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Phase when players are auctioning. When a player passes he no longer participates in the auction.
 */
public class AuctionPhase implements Phase {

	private final PlayerSwitcher playerSwitcher;
	private final PlayerTransition transition = new NormalTransition();

	public AuctionPhase(List<PlayerClock> allPlayers, PlayerClock currentPlayer) {
		this.playerSwitcher = new PlayerSwitcher(allPlayers, currentPlayer);
	}

	public void bid(long when) {
		checkThatAuctionIsNotOver();
		playerSwitcher.switchPlayers(transition, when);
	}

	public void pass(long when) {
		checkThatAuctionIsNotOver();
		playerSwitcher.removeCurrentPlayer();
		playerSwitcher.switchPlayers(transition, when);
	}

	@Override
	public List<PlayerClock> getRemainingPlayers() {
		return playerSwitcher.getRemainingPlayers();
	}

	@Override
	public PlayerClock getCurrentPlayer() {
		return playerSwitcher.getCurrentPlayer();
	}

	private void checkThatAuctionIsNotOver() {
		if (playerSwitcher.getPlayersCount() <= 1) {
			throw new IllegalArgumentException("Auction is over!");
		}
	}
}
