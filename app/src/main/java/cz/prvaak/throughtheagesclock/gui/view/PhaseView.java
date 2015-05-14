package cz.prvaak.throughtheagesclock.gui.view;

/**
 * Interface of a view that needs to change according to current phase.
 */
public interface PhaseView {

	/**
	 * Available game phases.
	 */
	enum Phase {
		NORMAL, AUCTION, ONE_ON_ONE
	}

	void setPhase(Phase phase);
}
