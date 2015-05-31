package cz.prvaak.throughtheagesclock.phase.switcher.transition;

import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Created by michal on 5/31/15.
 */
public class FinalAuctionTransition extends PlayerTransition {

	private final PlayerClock initiatingPlayer;

	public FinalAuctionTransition(PlayerClock initiatingPlayer) {
		this.initiatingPlayer = initiatingPlayer;
	}

	@Override
	public void beforeSwitch(PlayerClock activePlayer, TimeInstant when) {

	}

	@Override
	public void afterSwitch(PlayerClock activePlayer, TimeInstant when) {

	}
}
