package cz.prvaak.civclock.clock;

import java.util.ArrayList;

import cz.prvaak.civclock.TimeAmount;
import cz.prvaak.civclock.TimeInstant;

/**
 * Class for monitoring which method of {@link PlayerClock} has been called.
 */
public class FakePlayerClock extends PlayerClock {

	public boolean isStarted;
	public boolean isPaused;
	public boolean isUpkeepStarted;
	public boolean isReserveAdded;
	public boolean isBonusAdded;

	public static ArrayList<PlayerClock> createPlayerClocks(int howMany) {
		ArrayList<PlayerClock> playerClocks = new ArrayList<>(howMany);
		for (int i = 0; i < howMany; i++) {
			playerClocks.add(new FakePlayerClock(i));
		}
		playerClocks.get(0).start(new TimeInstant(0L));
		return playerClocks;
	}

	public static EpochTimeAmount createTimeAmountPerEpoch() {
		EpochTimeAmount epochTimeAmount = new EpochTimeAmount();
		epochTimeAmount.put(FakeEpoch.ONE, new TimeAmount(10000L));
		epochTimeAmount.put(FakeEpoch.TWO, new TimeAmount(10000L));
		return epochTimeAmount;
	}

	public FakePlayerClock() {
		this(0);
	}
	public FakePlayerClock(int index) {
		super(new FakePlayerId(index), new TimeAmount(10000L), new TimeAmount(1000L),
				createTimeAmountPerEpoch(), new TimeAmount(10000L), new TimeAmount(10000L));
	}

	@Override
	public void start(TimeInstant when) {
		isStarted = true;
	}

	@Override
	public void stop(TimeInstant when) {
		isStarted = false;
	}

	@Override
	public void pause(TimeInstant when) {
		isPaused = true;
	}

	@Override
	public void resume(TimeInstant when) {
		isPaused = false;
	}

	@Override
	public void addReserveTime(TimeInstant when, TimeAmount amount) {
		isReserveAdded = true;
	}

	@Override
	public void upkeep(TimeInstant when) {
		isUpkeepStarted = true;
	}

	@Override
	public void addUpkeepTime(TimeInstant when, TimeAmount amount) {
		isBonusAdded = true;
	}

	@Override
	public TimeAmount getRemainingReserveTime(TimeInstant when) {
		return TimeAmount.EMPTY;
	}

	@Override
	public TimeAmount getRemainingUpkeepTime(TimeInstant when) {
		return TimeAmount.EMPTY;
	}

	@Override
	public String toString() {
		return getPlayerId().toString();
	}
}
