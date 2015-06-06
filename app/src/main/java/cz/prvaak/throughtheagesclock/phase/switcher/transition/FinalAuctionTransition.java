package cz.prvaak.throughtheagesclock.phase.switcher.transition;

import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Transition that adds time to winner and starts the initiating player.
 */
public class FinalAuctionTransition implements PlayerTransition {

	private final PlayerClock initiatingPlayer;

	public FinalAuctionTransition(PlayerClock initiatingPlayer) {
		this.initiatingPlayer = initiatingPlayer;
	}

	@Override
	public void beforeSwitch(PlayerClock activePlayer, TimeInstant when) {
		activePlayer.stop(when);
	}

	@Override
	public void afterSwitch(PlayerClock activePlayer, TimeInstant when) {
		activePlayer.addUpkeepBonusTime(when);
		initiatingPlayer.start(when);
	}
}
