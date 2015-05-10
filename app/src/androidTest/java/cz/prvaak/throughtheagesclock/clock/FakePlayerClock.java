package cz.prvaak.throughtheagesclock.clock;

import java.util.ArrayList;

/**
 * Class for monitoring which method of {@link PlayerClock} has been called.
 */
public class FakePlayerClock extends PlayerClock {

	public boolean isStarted;
	public boolean isPaused;
	public boolean isUpkeepStarted;
	public boolean isReserveAdded;

	public static ArrayList<PlayerClock> createPlayerClocks(int howMany) {
		ArrayList<PlayerClock> playerClocks = new ArrayList<>(howMany);
		for (int i = 0; i < howMany; i++) {
			playerClocks.add(new FakePlayerClock());
		}
		playerClocks.get(0).start(0L);
		return playerClocks;
	}

	public FakePlayerClock() {
		super(new PlayerId(), 10000L, 1000L);
	}

	@Override
	public void start(long when) {
		isStarted = true;
	}

	@Override
	public void stop(long when) {
		isStarted = false;
	}

	@Override
	public void pause(long when) {
		isPaused = true;
	}

	@Override
	public void resume(long when) {
		isPaused = false;
	}

	@Override
	public void addReserveTime(long amount) {
		isReserveAdded = true;
	}

	@Override
	public void upkeep(long when) {
		isUpkeepStarted = true;
	}

	@Override
	public long getRemainingReserveTime(long when) {
		return 0;
	}

	@Override
	public long getRemainingUpkeepTime(long when) {
		return 0;
	}
}
