package cz.prvaak.throughtheagesclock.phase.switcher.transition;

import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.EpochId;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Transition that stops previous player and starts the next player.
 */
public class NormalTransition implements PlayerTransition {

	@Override
	public void beforeSwitch(PlayerClock activePlayer, TimeInstant when, EpochId epoch) {
		activePlayer.stop(when);
	}

	@Override
	public void afterSwitch(PlayerClock activePlayer, TimeInstant when, EpochId epoch) {
		activePlayer.start(when);
	}
}
