package cz.prvaak.throughtheagesclock;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.utils.RepeatingIterator;

/**
 * Clock switching logic during standard round about phase. Players are switching
 * one after another.
 */
public class RoundAboutPhase implements Phase {

	private final LinkedList<PlayerClock> playerClocks;
	private Iterator<PlayerClock> iterator;
	private PlayerClock currentPlayer;

	public RoundAboutPhase(List<PlayerClock> playerClocks, PlayerClock currentPlayer) {
		if (playerClocks.size() <= 1) {
			throw new IllegalArgumentException("Not enough players!");
		}

		this.playerClocks = new LinkedList<>(playerClocks);
		this.iterator = new RepeatingIterator<>(this.playerClocks);
		this.currentPlayer = this.iterator.next();
	}

	public void turnDone(long when) {
		currentPlayer = iterator.next();
		currentPlayer.stop(when);
		currentPlayer.upkeep(when);
		if (!iterator.hasNext()) {
			iterator = playerClocks.listIterator();
		}
		currentPlayer = iterator.next();
		currentPlayer.start(when);
	}

	@Override
	public List<PlayerClock> getRemainingPlayers() {
		return Collections.unmodifiableList(playerClocks);
	}

	@Override
	public PlayerClock getCurrentPlayer() {
		return currentPlayer;
	}
}
