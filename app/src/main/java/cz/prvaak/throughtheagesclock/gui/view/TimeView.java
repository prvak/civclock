package cz.prvaak.throughtheagesclock.gui.view;

import cz.prvaak.throughtheagesclock.TimeInstant;

/**
 * Interface of a view that contains time information.
 */
public interface TimeView {

	/** Updates the view based on current time. */
	void updateTime(TimeInstant now);
}
