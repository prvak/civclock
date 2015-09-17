package cz.prvaak.civclock.gui.view.display;

import cz.prvaak.civclock.clock.PlayerId;

/**
 * Interface of views that display player information.
 */
public interface PlayerDisplay {

	void updatePlayer(PlayerId playerId);
}
