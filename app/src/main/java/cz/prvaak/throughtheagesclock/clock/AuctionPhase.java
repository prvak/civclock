package cz.prvaak.throughtheagesclock.clock;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Phase when players are auctioning. When a player passes he no longer participates in the auction.
 */
public class AuctionPhase {

	private final List<PlayerClock> playerClocks;
	private Iterator<PlayerClock> iterator;
	private PlayerClock currentPlayer;

	public AuctionPhase(List<PlayerClock> playerClocks, int currentPlayer) {
		if (playerClocks.isEmpty()) {
			throw new IllegalArgumentException("Not enough players!");
		}
		this.playerClocks = new LinkedList<>(playerClocks);
		this.iterator = this.playerClocks.listIterator(currentPlayer);
	}

	public void bid(long when) {
		currentPlayer = iterator.next();
		currentPlayer.stop(when);
		if (!iterator.hasNext()) {
			iterator = playerClocks.listIterator();
		}
		currentPlayer = iterator.next();
		currentPlayer.start(when);
	}

	public void pass(long when) {
		currentPlayer = iterator.next();
		currentPlayer.stop(when);
		iterator.remove();
		if (!iterator.hasNext()) {
			iterator = playerClocks.listIterator();
		}
		currentPlayer = iterator.next();
		currentPlayer.start(when);
	}

	public boolean isOver() {
		return playerClocks.size() <= 1;
	}
}
