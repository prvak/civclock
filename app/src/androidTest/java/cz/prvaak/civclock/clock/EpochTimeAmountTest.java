package cz.prvaak.civclock.clock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import cz.prvaak.civclock.TimeAmount;

/**
 * Tests of {@link EpochTimeAmount} class.
 */
public class EpochTimeAmountTest extends InstrumentationTestCase {

	public void testEmpty() {
		EpochTimeAmount epochTimeAmount = new EpochTimeAmount();
		try {
			epochTimeAmount.get(FakeEpoch.ONE);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testInsertion() {
		EpochTimeAmount epochTimeAmount = new EpochTimeAmount();
		EpochId epoch = FakeEpoch.ONE;
		TimeAmount timeAmount = new TimeAmount(10000L);
		epochTimeAmount.put(epoch, timeAmount);
		assertEquals(timeAmount, epochTimeAmount.get(epoch));
	}
}
