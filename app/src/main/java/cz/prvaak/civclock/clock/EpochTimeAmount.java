package cz.prvaak.civclock.clock;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cz.prvaak.civclock.TimeAmount;

/**
 * Storage for different time amounts for different epochs.
 */
public class EpochTimeAmount implements Serializable, Iterable<TimeAmount> {
	private final Map<EpochId, TimeAmount> amounts;

	public EpochTimeAmount() {
		this.amounts = new HashMap<>();
	}

	public EpochTimeAmount(EpochTimeAmount other) {
		this.amounts = new HashMap<>(other.amounts);
	}

	public void put(EpochId epoch, TimeAmount amount) {
		amounts.put(epoch, amount);
	}

	public TimeAmount get(EpochId epoch) {
		return amounts.get(epoch);
	}

	@Override
	public Iterator<TimeAmount> iterator() {
		return amounts.values().iterator();
	}
}
