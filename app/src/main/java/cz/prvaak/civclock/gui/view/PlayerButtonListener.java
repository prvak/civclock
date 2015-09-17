package cz.prvaak.civclock.gui.view;

import cz.prvaak.civclock.clock.PlayerId;

/**
 * Listener for player related buttons.
 */
public interface PlayerButtonListener {

	/** Button related to given player have been clicked. */
	void onPlayerButtonClicked(PlayerId playerId);
}
