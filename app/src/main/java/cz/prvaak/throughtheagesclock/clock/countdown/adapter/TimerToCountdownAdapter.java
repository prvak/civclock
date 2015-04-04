package cz.prvaak.throughtheagesclock.clock.countdown.adapter;

import cz.prvaak.throughtheagesclock.clock.UniversalClock;
import cz.prvaak.throughtheagesclock.clock.countdown.CountdownClock;
import cz.prvaak.throughtheagesclock.clock.timer.TimerClock;

/**
 *
 */
public class TimerToCountdownAdapter implements CountdownClock {
	private final TimerClock timer;
	private long baseTime;

	public TimerToCountdownAdapter(TimerClock timer, long baseTime) {
		this.timer = timer;
		this.baseTime = baseTime;
	}

	@Override
	public long getRemainingTime(long when) {
		return baseTime - timer.getTime(when);
	}

	@Override
	public void addTime(long amount) {
		baseTime += amount;
	}

	@Override
	public void stop(long when) {
		timer.stop(when);
	}

	@Override
	public void unstop(long when) {
		timer.unstop(when);
	}

	@Override
	public void start(long when) {
		timer.start(when);
	}

	@Override
	public void pause(long when) {
		timer.pause(when);
	}

	@Override
	public void unpause(long when) {
		timer.unpause(when);
	}
}
