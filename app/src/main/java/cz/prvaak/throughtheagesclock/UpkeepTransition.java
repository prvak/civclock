package cz.prvaak.throughtheagesclock;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Transition that triggers upkeep and adds reserve time.
 */
public class UpkeepTransition implements PlayerTransition {

	@Override
	public void beforeSwitch(PlayerClock activePlayer, long when) {
		activePlayer.stop(when);
		activePlayer.upkeep(when);
		// FIXME: Upkeep amount should be configurable.
		activePlayer.addReserveTime(1000L);
	}

	@Override
	public void afterSwitch(PlayerClock activePlayer, long when) {
		activePlayer.start(when);
	}
}
