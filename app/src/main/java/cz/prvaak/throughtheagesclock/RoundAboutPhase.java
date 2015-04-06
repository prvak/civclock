package cz.prvaak.throughtheagesclock;

import java.util.Iterator;
import java.util.LinkedList;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Clock switching logic during standard round about phase. Players are switching
 * one after another.
 */
public class RoundAboutPhase {

	private final LinkedList<PlayerClock> playerClocks;
	private Iterator<PlayerClock> iterator;
	private PlayerClock currentPlayer;

	public RoundAboutPhase(LinkedList<PlayerClock> playerClocks, int currentPlayer) {
		if (playerClocks.isEmpty()) {
			throw new IllegalArgumentException("Not enough players!");
		}
		this.playerClocks = new LinkedList<>(playerClocks);
		this.iterator = this.playerClocks.listIterator(currentPlayer);
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
}
