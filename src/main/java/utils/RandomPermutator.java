package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class RandomPermutator implements Iterable<List<List<Integer>>> {
	
	private final int k;
	private final int n;
	private final List<List<Integer>> s;
	private final Random random = new Random(777);
	
	/*
	 * Produces lists of size k with elements from s n times
	 */
	RandomPermutator(List<List<Integer>> s, int k, int n) {
		this.s = s;
		this.k = k;
		this.n = n;
	}

	@Override
	public Iterator<List<List<Integer>>> iterator() {
		return new RandomPermutatorIterator(this, n);
	}
	
	List<List<Integer>> getShuffledSet() {
		List<List<Integer>> shuffled = new ArrayList<List<Integer>>();
		for (int i = 0; i < this.k; i++) {
			shuffled.add(s.get(random.nextInt(this.s.size())));
		}
		return shuffled;
	}

}
