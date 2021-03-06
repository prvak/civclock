package cz.prvaak.civclock.clock.timer;

import cz.prvaak.civclock.TimeAmount;
import cz.prvaak.civclock.TimeInstant;
import cz.prvaak.civclock.clock.counter.CounterClock;

/**
 * Interface for clocks that count remaining time.
 */
public interface TimerClock extends CounterClock {

	/**
	 * Restart the timer with new time limit.
	 *  @param when Time in milliseconds when the timer was restarted.
	 * @param newBaseTime How much time in milliseconds is remaining.
	 */
	void restart(TimeInstant when, TimeAmount newBaseTime);

	/**
	 * Get remaining time.
	 *
	 * @param when Time in milliseconds when the question was asked.
	 * @return Remaining time in milliseconds. Can be negative.
	 */
	TimeAmount getRemainingTime(TimeInstant when);

	/**
	 * Add given amount of time to remaining time.
	 *
	 * @param when Time in milliseconds when the time was added.
	 * @param amount How many milliseconds to add.
	 */
	void addTime(TimeInstant when, TimeAmount amount);
}
