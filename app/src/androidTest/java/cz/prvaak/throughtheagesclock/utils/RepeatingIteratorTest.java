package cz.prvaak.throughtheagesclock.utils;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.utils.RepeatingIterator} class.
 */
public class RepeatingIteratorTest extends InstrumentationTestCase {

	public void testEmptyIterator() {
		List<Integer> list = Arrays.asList(new Integer[]{});
		RepeatingIterator<Integer> iterator = new RepeatingIterator<>(list);
		assertFalse(iterator.hasNext());
		try {
			iterator.next();
			Assert.fail("Should have thrown NoSuchElementException.");
		} catch (NoSuchElementException e) {
			// success
		}
	}

	public void testRepeat() {
		Integer[] array = {0, 1, 2};
		List<Integer> list = Arrays.asList(array);
		RepeatingIterator<Integer> iterator = new RepeatingIterator<>(list);
		assertEquals(array[0], iterator.next());
		assertEquals(array[1], iterator.next());
		assertEquals(array[2], iterator.next());
		assertEquals(array[0], iterator.next());
		assertEquals(array[1], iterator.next());
		assertEquals(array[2], iterator.next());
		assertEquals(array[0], iterator.next());
	}

	public void testRemove() {
		Integer[] array = {0, 1, 2};
		LinkedList<Integer> list = new LinkedList<>(Arrays.asList(array));
		RepeatingIterator<Integer> iterator = new RepeatingIterator<>(list);
		assertEquals(array[0], iterator.next());
		assertEquals(array[1], iterator.next());
		iterator.remove();
		assertEquals(2, list.size());
		assertEquals(array[2], iterator.next());
		assertEquals(array[0], iterator.next());
		assertEquals(array[2], iterator.next());
	}

	public void testRemoveLastElement() {
		Integer[] array = {0};
		LinkedList<Integer> list = new LinkedList<>(Arrays.asList(array));
		RepeatingIterator<Integer> iterator = new RepeatingIterator<>(list);
		iterator.next();
		iterator.remove();
		assertFalse(iterator.hasNext());
		try {
			iterator.next();
			Assert.fail("Should have thrown NoSuchElementException.");
		} catch (NoSuchElementException e) {
			// success
		}
	}
}
