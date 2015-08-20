package cz.prvaak.throughtheagesclock.phase.switcher.transition;

import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.EpochId;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Transition that triggers upkeep and adds reserve time.
 */
public class UpkeepTransition implements PlayerTransition {

	@Override
	public void beforeSwitch(PlayerClock activePlayer, TimeInstant when, EpochId epoch) {
		activePlayer.stop(when);
		activePlayer.upkeep(when);
		activePlayer.addTurnBonusTime(when, epoch);
	}

	@Override
	public void afterSwitch(PlayerClock activePlayer, TimeInstant when, EpochId epoch) {
		activePlayer.start(when);
	}
}
