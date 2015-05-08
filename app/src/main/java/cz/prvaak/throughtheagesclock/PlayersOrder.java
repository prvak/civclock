package cz.prvaak.throughtheagesclock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.utils.RepeatingIterator;

/**
 * Class that maintains order of players.
 */
public class PlayersOrder {

	private final LinkedList<PlayerClock> playerClocks;
	private RepeatingIterator<PlayerClock> iterator;
	private PlayerClock currentPlayer;

	public PlayersOrder(List<PlayerClock> playerClocks, PlayerClock currentPlayer) {
		if (!playerClocks.contains(currentPlayer)) {
			throw new IllegalArgumentException("PlayerClock not found in the collection!");
		}

		this.playerClocks = new LinkedList<>(playerClocks);
		this.iterator = new RepeatingIterator<>(this.playerClocks);
		while (this.currentPlayer != currentPlayer) {
			this.currentPlayer = this.iterator.next();
		}
	}

	/** Get number of remaining players. */
	public int getPlayersCount() {
		return playerClocks.size();
	}

	/** Get all remaining players. */
	public List<PlayerClock> getRemainingPlayers() {
		return Collections.unmodifiableList(playerClocks);
	}

	/** Get currently active player. */
	public PlayerClock getCurrentPlayer() {
		return currentPlayer;
	}

	/** Remove current player. */
	public void removeCurrentPlayer() {
		iterator.remove();
	}

	/** Stops current player and starts next player. */
	public void switchPlayers(long when) {
		currentPlayer.stop(when);
		currentPlayer = iterator.next();
		currentPlayer.start(when);
	}
}
