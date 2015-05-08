package cz.prvaak.throughtheagesclock.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Iterator that iterates through the same list repeatedly.
 */
public class RepeatingIterator<E> implements Iterator<E>{

	private final Collection<E> collection;
	private Iterator<E> iterator;

	public RepeatingIterator(Collection<E> collection) {
		this.collection = collection;
		this.iterator = collection.iterator();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext() || !collection.isEmpty();
	}

	@Override
	public E next() {
		if (!iterator.hasNext()) {
			// start from the beginning
			iterator = collection.iterator();
		}
		return iterator.next();
	}

	@Override
	public void remove() {
		iterator.remove();
	}
}
