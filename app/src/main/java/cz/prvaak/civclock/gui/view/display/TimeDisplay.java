package cz.prvaak.civclock.gui.view.display;

import cz.prvaak.civclock.TimeInstant;

/**
 * Interface of a view that displays time information.
 */
public interface TimeDisplay {

	/** Updates the view based on current time. */
	void updateTime(TimeInstant now);
}
