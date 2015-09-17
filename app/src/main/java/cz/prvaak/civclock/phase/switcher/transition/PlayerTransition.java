package cz.prvaak.civclock.phase.switcher.transition;

import java.io.Serializable;

import cz.prvaak.civclock.TimeInstant;
import cz.prvaak.civclock.clock.EpochId;
import cz.prvaak.civclock.clock.PlayerClock;

/**
 * Transition between active players.
 */
public interface PlayerTransition extends Serializable {

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
