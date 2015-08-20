package cz.prvaak.throughtheagesclock.gui;

import cz.prvaak.throughtheagesclock.clock.EpochId;

/**
 * Epochs specific to Through the Ages.
 */
public enum Age implements EpochId {
	A, I, II, III, IV;

	public boolean hasNextAge() {
		return this != IV;
	}

	public Age getNextAge() {
		if (!hasNextAge()) {
			throw new IllegalStateException("There is no age after the Age IV!");
		}
		return Age.values()[this.ordinal() + 1];
	}
}
