package cz.prvaak.throughtheagesclock.gui.view.display;

import cz.prvaak.throughtheagesclock.TimeInstant;

/**
 * Interface of a view that displays time information.
 */
public interface TimeDisplay {

	/** Updates the view based on current time. */
	void updateTime(TimeInstant now);
}
