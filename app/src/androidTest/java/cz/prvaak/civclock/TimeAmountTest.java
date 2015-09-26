package cz.prvaak.civclock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Tests of {@link TimeAmount} class.
 */
public class TimeAmountTest extends InstrumentationTestCase {

	public void testGetHours() throws Exception {
		TimeAmount amount = new TimeAmount(5 * 60 * 60 * 1000L);
		assertEquals(5L, amount.getHours());
		assertEquals(0L, amount.getMinutes());
		assertEquals(0L, amount.getSeconds());
		assertEquals(0L, amount.getMilliseconds());
	}

	public void testGetMinutes() throws Exception {
		TimeAmount amount = new TimeAmount(35 * 60 * 1000L);
		assertEquals(0L, amount.getHours());
		assertEquals(35L, amount.getMinutes());
		assertEquals(0L, amount.getSeconds());
		assertEquals(0L, amount.getMilliseconds());
	}

	public void testGetSeconds() throws Exception {
		TimeAmount amount = new TimeAmount(15 * 1000L);
		assertEquals(0L, amount.getHours());
		assertEquals(0L, amount.getMinutes());
		assertEquals(15L, amount.getSeconds());
		assertEquals(0L, amount.getMilliseconds());
	}

	public void testGetMilliseconds() throws Exception {
		TimeAmount amount = new TimeAmount(521L);
		assertEquals(0L, amount.getHours());
		assertEquals(0L, amount.getMinutes());
		assertEquals(0L, amount.getSeconds());
		assertEquals(521L, amount.getMilliseconds());
	}

	public void testGetAll() throws Exception {
		TimeAmount amount = new TimeAmount(
				3 * 60 * 60 * 1000L
				+ 12 * 60 * 1000L
				+ 58 * 1000L
				+ 135L);
		assertEquals(3L, amount.getHours());
		assertEquals(12L, amount.getMinutes());
		assertEquals(58L, amount.getSeconds());
		assertEquals(135L, amount.getMilliseconds());
		assertFalse(amount.isNegative());
	}

	public void testConstructorFromFields() throws Exception {
		TimeAmount amount = new TimeAmount(2L, 44L, 32L, 500L);
		assertEquals(2L, amount.getHours());
		assertEquals(44L, amount.getMinutes());
		assertEquals(32L, amount.getSeconds());
		assertEquals(500L, amount.getMilliseconds());
		assertFalse(amount.isNegative());
	}

	public void testNegativeInterval() throws Exception {
		TimeAmount amount = new TimeAmount(
				-(1 * 60 * 60 * 1000L
				+ 8 * 60 * 1000L
				+ 17 * 1000L
				+ 258L));
		assertEquals(1L, amount.getHours());
		assertEquals(8L, amount.getMinutes());
		assertEquals(17L, amount.getSeconds());
		assertEquals(258L, amount.getMilliseconds());
		assertTrue(amount.isNegative());
	}

	public void testMinMax() throws Exception {
		TimeAmount amountSmall = new TimeAmount(100L);
		TimeAmount amountBig = new TimeAmount(10000L);
		assertEquals(amountSmall, TimeAmount.min(amountSmall, amountBig));
		assertEquals(amountSmall, TimeAmount.min(amountBig, amountSmall));
		assertEquals(amountBig, TimeAmount.max(amountSmall, amountBig));
		assertEquals(amountBig, TimeAmount.max(amountBig, amountSmall));
	}

	public void testMinMaxNegative() throws Exception {
		TimeAmount amountSmall = new TimeAmount(-100L);
		TimeAmount amountBig = new TimeAmount(-10000L);
		assertEquals(amountBig, TimeAmount.min(amountSmall, amountBig));
		assertEquals(amountBig, TimeAmount.min(amountBig, amountSmall));
		assertEquals(amountSmall, TimeAmount.max(amountSmall, amountBig));
		assertEquals(amountSmall, TimeAmount.max(amountBig, amountSmall));
	}

	public void testFormat() throws Exception {
		TimeAmount amount = new TimeAmount(2 * 60 * 60 * 1000 + 27 * 60 * 1000 + 36 * 1000 + 257);
		assertEquals("2:27:36", amount.format(TimeAmount.Formatting.PRECISE));
	}

	public void testWithoutHours() throws Exception {
		TimeAmount amount = new TimeAmount(7 * 60 * 1000 + 5 * 1000 + 348);
		assertEquals("7:05", amount.format(TimeAmount.Formatting.PRECISE));
	}

	public void testWithoutMinutes() throws Exception {
		TimeAmount amount = new TimeAmount(3 * 1000 + 550);
		assertEquals("03.5", amount.format(TimeAmount.Formatting.PRECISE));
	}

	public void testFormatNegative() throws Exception {
		TimeAmount amount = new TimeAmount(-5 * 60 * 60 * 1000 - 12 * 60 * 1000 - 4 * 1000 - 852);
		assertEquals("-5:12:04", amount.format(TimeAmount.Formatting.PRECISE));
	}

	public void testSimpleFormat() throws Exception {
		TimeAmount amount = new TimeAmount(3 * 1000 + 550);
		assertEquals("3", amount.format(TimeAmount.Formatting.SIMPLE));
	}

	public void testFromText() throws Exception {
		TimeAmount amount = new TimeAmount(2 * 60 * 60 * 1000 + 27 * 60 * 1000 + 36 * 1000);
		assertEquals(amount, new TimeAmount("2:27:36"));
	}

	public void testFromTextWithoutHours() throws Exception {
		TimeAmount amount = new TimeAmount(7 * 60 * 1000 + 5 * 1000);
		assertEquals(amount, new TimeAmount("7:05"));
		assertEquals(amount, new TimeAmount("07:05"));
		assertEquals(amount, new TimeAmount("0:07:05"));
		assertEquals(amount, new TimeAmount("705"));
		assertEquals(amount, new TimeAmount("00705"));
	}

	public void testFromTextWithoutMilliseconds() throws Exception {
		TimeAmount amount = new TimeAmount(3 * 1000);
		assertEquals(amount, new TimeAmount("3"));
		assertEquals(amount, new TimeAmount("03"));
		assertEquals(amount, new TimeAmount("0:03"));
		assertEquals(amount, new TimeAmount("00:03"));
		assertEquals(amount, new TimeAmount("003"));
		assertEquals(amount, new TimeAmount("0003"));
	}

	public void testFromTextEmpty() throws Exception {
		TimeAmount amount = new TimeAmount(0L);
		assertEquals(amount, new TimeAmount(""));
	}

	public void testFromTextNegative() throws Exception {
		TimeAmount amount = new TimeAmount(-5 * 60 * 60 * 1000 - 12 * 60 * 1000 - 4 * 1000);
		assertEquals(amount, new TimeAmount("-5:12:04"));
	}

	public void testFromTextLongHours() throws Exception {
		TimeAmount amount = new TimeAmount(123 * 60 * 60 * 1000 + 12 * 60 * 1000 + 4 * 1000);
		assertEquals(amount, new TimeAmount("123:12:04"));
	}

	public void testFromTextTooManyColons() {
		try {
			new TimeAmount("0:00:00:03");
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testFromTextNotANumber() {
		try {
			new TimeAmount("00:X0:30");
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testFromTextNoDigits() {
		try {
			new TimeAmount("10::30");
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testFromTextTooManyDigits() {
		try {
			new TimeAmount("10:001:30");
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testFromTextBigMinutes() {
		try {
			new TimeAmount("10:61:30");
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testFromTextBigSeconds() {
		try {
			new TimeAmount("10:16:62");
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
