package utils;

import com.google.common.collect.Iterables;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class RelationGeneratorIterator implements Iterator<Set<List<Integer>>> {
	
	private final Iterator<List<List<Integer>>> cartProdIterator;
	
	RelationGeneratorIterator(Iterator<List<List<Integer>>> cartProdIterator) {
		this.cartProdIterator = cartProdIterator;
	}

	@Override
	public boolean hasNext() {
		return cartProdIterator.hasNext();
	}

	@Override
	public Set<List<Integer>> next() {
		List<List<Integer>> next;
		Set<List<Integer>> nextSet;
		do {
			next = cartProdIterator.next();
			nextSet = new HashSet<>(next);
		} while (cartProdIterator.hasNext() && (next.size() != nextSet.size() || trivial(nextSet)));
		return nextSet;
	}

	private static boolean trivial(Set<List<Integer>> relation) {
		if (relation.size() == 0) return true;

		int arity = Iterables.getLast(relation).size();
		for (int i = 0; i < arity; i++) {
			if (relation.stream().map(x -> x.get(0)).distinct().count() == 1) return true;
		}
		return false;
	}

}
