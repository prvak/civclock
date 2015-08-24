package cz.prvaak.throughtheagesclock.utils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterator that iterates through the same list repeatedly.
 */
public class RepeatingIterator<E> implements Iterator<E>, Serializable {

	private final List<E> collection;
	private int index;

	public RepeatingIterator(List<E> collection) {
		this.collection = collection;
		this.index = -1;
	}

	@Override
	public boolean hasNext() {
		return !collection.isEmpty();
	}

	@Override
	public E next() {
		if (collection.isEmpty()) {
			throw new NoSuchElementException("There is no next element. The collection is empty!");
		}

		index++;
		normalizeIndex();
		return collection.get(index);
	}

	@Override
	public void remove() {
		normalizeIndex();
		collection.remove(index);
		index--;
	}

	private void normalizeIndex() {
		if (index >= collection.size()) {
			// start from the beginning
			index = 0;
		}
	}
}
