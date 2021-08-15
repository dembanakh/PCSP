package utils;

import java.util.Iterator;
import java.util.List;

public class RandomPermutatorIterator implements Iterator<List<List<Integer>>> {
	
	private int counter;
	private RandomPermutator permutator;
	
	RandomPermutatorIterator(RandomPermutator perm, int n_iter) {
		counter = n_iter;
		permutator = perm;
	}

	@Override
	public boolean hasNext() {
		return counter > 0;
	}

	@Override
	public List<List<Integer>> next() {
		counter--;
		return this.permutator.getShuffledSet();
	}

}
