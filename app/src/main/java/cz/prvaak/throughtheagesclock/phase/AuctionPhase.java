package cz.prvaak.throughtheagesclock.phase;

import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.phase.switcher.PlayerSwitcher;
import cz.prvaak.throughtheagesclock.phase.switcher.transition.NormalTransition;
import cz.prvaak.throughtheagesclock.phase.switcher.transition.PlayerTransition;

/**
 * Phase when players are auctioning. When a player passes he no longer participates in the auction.
 */
public class AuctionPhase implements Phase {

	private final PlayerSwitcher playerSwitcher;
	private final PlayerTransition transition = new NormalTransition();

	public AuctionPhase(List<PlayerClock> allPlayers, PlayerClock currentPlayer) {
		this.playerSwitcher = new PlayerSwitcher(allPlayers, currentPlayer);
	}

	/**
	 * Terminate turn of current player and keep him between active players.
	 *
	 * @param when Time in milliseconds when the bid was done.
	 */
	public void bid(long when) {
		checkThatAuctionIsNotOver();
		playerSwitcher.switchPlayers(transition, when);
	}

	/**
	 * Terminate turn of current player and remove him from active players.
	 * Current player will no longer participate in current phase.
	 *
	 * @param when Time in milliseconds when the pass was done.
	 */
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
