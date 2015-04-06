package cz.prvaak.throughtheagesclock.clock.countdown.adapter;

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
	public long getTime(long when) {
		return timer.getTime(when);
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
	public void start(long when) {
		timer.start(when);
	}

	@Override
	public void restart(long when) {
		timer.restart(when);
	}

	@Override
	public void pause(long when) {
		timer.pause(when);
	}

	@Override
	public void resume(long when) {
		timer.resume(when);
	}
}
