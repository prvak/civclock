package cz.prvaak.civclock.clock;

/**
 * Some fake player ID to use in tests.
 */
public class FakePlayerId implements PlayerId {
	private final int id;

	public FakePlayerId() {
		this(-1);
	}

	public FakePlayerId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("FakePlayer%d", id);
	}
}
