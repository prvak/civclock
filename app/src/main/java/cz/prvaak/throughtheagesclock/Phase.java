package cz.prvaak.throughtheagesclock;

import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Phase of the game. Phase determines what type of clock is displayed.
 */
public interface Phase {

	/** Get all players who participate in this phase. */
	List<PlayerClock> getAllPlayers();

	/** Get currently active player. */
	PlayerClock getCurrentPlayer();
}
