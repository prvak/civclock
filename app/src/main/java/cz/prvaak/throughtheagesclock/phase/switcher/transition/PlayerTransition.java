package cz.prvaak.throughtheagesclock.phase.switcher.transition;

import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.EpochId;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Transition between active players.
 */
public interface PlayerTransition {

	/**
	 * Stop given player and possibly perform other operations.
	 * This method is called just before given player becomes inactive.
	 */
	void beforeSwitch(PlayerClock activePlayer, TimeInstant when, EpochId epoch);

	/**
	 * Start given player and possibly perform other operations.
	 * This method is called just after given player become active.
	 */
	void afterSwitch(PlayerClock activePlayer, TimeInstant when, EpochId epoch);
}
