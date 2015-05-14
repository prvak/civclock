package cz.prvaak.throughtheagesclock.phase;

import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Phase of the game. Phase determines what type of clock is displayed.
 */
public interface GamePhase {

	/** Get currently active player. */
	PlayerClock getCurrentPlayer();

	/**
	 * Get players who participate in this phase except the current player. Players are in the
	 * same order in which they will became active.
	 */
	List<PlayerClock> getNextPlayers();

	/** Get all players who participate in this phase. */
	List<PlayerClock> getAllPlayers();

}
