package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TupleGenerator implements Iterable<List<Integer>> {
	
	private final int k;
	private final List<Integer> current;
	
	private boolean first = true;
	
	public TupleGenerator(int arity) {
		k = arity;
		current = new ArrayList<>();
		for (int i = 0; i < k; i++) current.add(0);
	}
	
	public boolean hasNext() {
		for (int i = 0; i < k; i++) if (current.get(i) == 0) return true;
		return false;
	}
	
	List<Integer> getCurrent() {
		return current;
	}
	
	void nextPermutation() {
		for (int i = k - 1; i >= 0; i--) {
			if (current.get(i) == 0) {
				current.set(i, 1);
				for (int j = i + 1; j < k; j++) current.set(j, 0);
				break;
			}
		}
	}
	
	boolean firstCall() {
		boolean value = first;
		first = false;
		return value;
	}

	@Override
	public Iterator<List<Integer>> iterator() {
		return new TupleGeneratorIterator(this);
	}

}
