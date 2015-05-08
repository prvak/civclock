package cz.prvaak.throughtheagesclock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.utils.RepeatingIterator;

/**
 * Class that performs transitions between active players.
 */
public class PlayerSwitcher {

	private final LinkedList<PlayerClock> remainingPlayers;
	private RepeatingIterator<PlayerClock> iterator;
	private PlayerClock currentPlayer;

	public PlayerSwitcher(List<PlayerClock> allPlayers, PlayerClock currentPlayer) {
		if (!allPlayers.contains(currentPlayer)) {
			throw new IllegalArgumentException("PlayerClock not found in the collection!");
		}

		this.remainingPlayers = new LinkedList<>(allPlayers);
		this.iterator = new RepeatingIterator<>(this.remainingPlayers);
		while (this.currentPlayer != currentPlayer) {
			this.currentPlayer = this.iterator.next();
		}
	}

	/** Get number of remaining players. */
	public int getPlayersCount() {
		return remainingPlayers.size();
	}

	/** Get all remaining players. */
	public List<PlayerClock> getRemainingPlayers() {
		return Collections.unmodifiableList(remainingPlayers);
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
	public void switchPlayers(PlayerTransition transition, long when) {
		transition.beforeSwitch(currentPlayer, when);
		currentPlayer = iterator.next();
		transition.afterSwitch(currentPlayer, when);
	}
}
