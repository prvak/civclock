package cz.prvaak.throughtheagesclock.clock.timer;

import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.counter.Counter;

/**
 * Timer with fixed base time and variable extensible time. Time is always deducted from the
 * extensible portion first.
 */
public class UpkeepTimer implements TimerClock {

	private final Counter counter;
	/** Fixed time is reset to this value on restart. */
	private TimeAmount fixedTime;
	/** How much of the fixed portion of the time remains. */
	private TimeAmount remainingFixedTime;
	/** How much of the variable portion of the time remains. */
	private TimeAmount remainingVariableTime;
	/** Time elapsed since previous restart. */
	private TimeAmount totalElapsedTime;

	public UpkeepTimer(TimeAmount fixedTime) {
		if (fixedTime.isNegative()) {
			throw new IllegalArgumentException("Fixed time cannot be negative!");
		}
		this.counter = new Counter();
		this.fixedTime = fixedTime;
		this.remainingFixedTime = TimeAmount.EMPTY;
		this.remainingVariableTime = TimeAmount.EMPTY;
		this.totalElapsedTime = TimeAmount.EMPTY;
	}

	private TimeAmount getRemainingVariableTime(TimeAmount elapsedTime) {
		return TimeAmount.max(TimeAmount.EMPTY, remainingVariableTime.subtract(elapsedTime));
	}

	private TimeAmount getRemainingFixedTime(TimeAmount elapsedTime) {
		TimeAmount negativeVariableTime = remainingVariableTime.subtract(elapsedTime);
		if (negativeVariableTime.isNegative()) {
			// All variable time have been used up.
			return TimeAmount.max(TimeAmount.EMPTY, remainingFixedTime.add(negativeVariableTime));
		} else {
			// Variable time is still remaining.
			return remainingFixedTime;
		}
	}

	@Override
	public void restart(TimeInstant when) {
		TimeAmount elapsedTime = counter.getElapsedTime(when);
		TimeAmount variableTime = getRemainingVariableTime(elapsedTime);
		counter.restart(when);
		remainingFixedTime = fixedTime;
		remainingVariableTime = variableTime;
		totalElapsedTime = TimeAmount.EMPTY;
	}

	@Override
	public void restart(TimeInstant when, TimeAmount newBaseTime) {
		TimeAmount elapsedTime = counter.getElapsedTime(when);
		TimeAmount variableTime = getRemainingVariableTime(elapsedTime);
		counter.restart(when);
		fixedTime = newBaseTime;
		remainingFixedTime = fixedTime;
		remainingVariableTime = variableTime;
		totalElapsedTime = TimeAmount.EMPTY;
	}

	@Override
	public void addTime(TimeInstant when, TimeAmount amount) {
		TimeAmount elapsedTime = counter.getElapsedTime(when);
		TimeAmount variableTime = getRemainingVariableTime(elapsedTime);
		TimeAmount fixedTime = getRemainingFixedTime(elapsedTime);
		counter.restart(when);
		remainingFixedTime = fixedTime;
		remainingVariableTime = variableTime.add(amount);
		totalElapsedTime.add(elapsedTime);
	}

	@Override
	public TimeAmount getRemainingTime(TimeInstant when) {
		TimeAmount elapsedTime = counter.getElapsedTime(when);
		TimeAmount variableTime = getRemainingVariableTime(elapsedTime);
		TimeAmount fixedTime = getRemainingFixedTime(elapsedTime);
		return fixedTime.add(variableTime);
	}

	@Override
	public TimeAmount getElapsedTime(TimeInstant when) {
		return totalElapsedTime;
	}

	@Override
	public void stop(TimeInstant when) {
		counter.stop(when);
	}

	@Override
	public void start(TimeInstant when) {
		remainingFixedTime = fixedTime;
		counter.restart(when); // counter may be already started by call to addTime
	}

	@Override
	public void pause(TimeInstant when) {
		counter.pause(when);
	}

	@Override
	public void resume(TimeInstant when) {
		counter.resume(when);
	}
}
