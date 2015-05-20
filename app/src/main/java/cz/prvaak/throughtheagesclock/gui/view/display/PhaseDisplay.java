package cz.prvaak.throughtheagesclock.gui.view.display;

/**
 * Interface of a view that needs to change according to current phase.
 */
public interface PhaseDisplay {

	/**
	 * Available game phases.
	 */
	enum Phase {
		NORMAL, AUCTION, ONE_ON_ONE
	}

	/** Update view based on given phase. */
	void updatePhase(Phase phase);
}
