package cz.prvaak.throughtheagesclock.clock.counter;

/**
 * Adapter that delegates all calls to another timer.
 */
public class CounterAdapter implements CounterClock {

	private final CounterClock target;

	public CounterAdapter(CounterClock target) {
		this.target = target;
	}

	@Override
	public void restart(long when) {
		target.restart(when);
	}

	@Override
	public long getElapsedTime(long when) {
		return target.getElapsedTime(when);
	}

	@Override
	public void stop(long when) {
		target.stop(when);
	}

	@Override
	public void start(long when) {
		target.start(when);
	}

	@Override
	public void pause(long when) {
		target.pause(when);
	}

	@Override
	public void resume(long when) {
		target.resume(when);
	}

	@Override
	public String toString() {
		return target.toString();
	}
}
