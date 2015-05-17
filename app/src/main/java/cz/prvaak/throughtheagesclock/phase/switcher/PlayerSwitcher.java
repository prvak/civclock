package cz.prvaak.throughtheagesclock.phase.switcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.phase.switcher.transition.PlayerTransition;
import cz.prvaak.throughtheagesclock.utils.RepeatingIterator;

/**
 * Class that performs transitions between active players.
 */
public class PlayerSwitcher {

	private final LinkedList<PlayerClock> allPlayers;
	private RepeatingIterator<PlayerClock> iterator;
	private PlayerClock currentPlayer;

	public PlayerSwitcher(List<? extends PlayerClock> allPlayers, PlayerClock currentPlayer) {
		if (!allPlayers.contains(currentPlayer)) {
			throw new IllegalArgumentException("PlayerClock not found in the collection!");
		}

		this.allPlayers = new LinkedList<>(allPlayers);
		this.iterator = new RepeatingIterator<>(this.allPlayers);
		while (this.currentPlayer != currentPlayer) {
			this.currentPlayer = this.iterator.next();
		}
	}

	/** Get number of remaining players. */
	public int getPlayersCount() {
		return allPlayers.size();
	}

	/** Get all players. */
	public List<PlayerClock> getAllPlayers() {
		return Collections.unmodifiableList(allPlayers);
	}

	/** Get all players. */
	public List<PlayerClock> getNextPlayers() {
		ArrayList<PlayerClock> nextPlayers = new ArrayList<>(allPlayers.size() - 1);
		RepeatingIterator<PlayerClock> iter = new RepeatingIterator<>(allPlayers);

		while (iter.hasNext()) {
			// find the starting player
			PlayerClock nextPlayer = iter.next();
			if (nextPlayer.equals(currentPlayer)) {
				break;
			}
		}
		while (iter.hasNext()) {
			// iterate to the current player again and add all players to the output list
			PlayerClock nextPlayer = iter.next();
			if (nextPlayer.equals(currentPlayer)) {
				break;
			}
			nextPlayers.add(nextPlayer);
		}
		return nextPlayers;
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
	public void switchPlayers(PlayerTransition transition, TimeInstant when) {
		transition.beforeSwitch(currentPlayer, when);
		currentPlayer = iterator.next();
		transition.afterSwitch(currentPlayer, when);
	}
}
