package cz.prvaak.throughtheagesclock.clock;

import java.util.ArrayList;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;

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
			playerClocks.add(new FakePlayerClock());
		}
		playerClocks.get(0).start(new TimeInstant(0L));
		return playerClocks;
	}

	public FakePlayerClock() {
		super(new FakePlayerId(), new TimeAmount(10000L), new TimeAmount(1000L), new TimeAmount(10000L));
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
	public void addUpkeepBonusTime(TimeInstant when, TimeAmount amount) {
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
}
