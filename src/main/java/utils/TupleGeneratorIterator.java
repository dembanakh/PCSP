package utils;

import java.util.Iterator;
import java.util.List;

class TupleGeneratorIterator implements Iterator<List<Integer>> {
	
	private final TupleGenerator generator;
	
	TupleGeneratorIterator(TupleGenerator generator) {
		this.generator = generator;
	}

	@Override
	public boolean hasNext() {
		return generator.hasNext();
	}

	@Override
	public List<Integer> next() {
		if (generator.firstCall()) return generator.getCurrent();
		List<Integer> value = generator.getCurrent();
		generator.nextPermutation();
		return value;
	}

}
