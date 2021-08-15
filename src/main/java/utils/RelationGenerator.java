package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

public class RelationGenerator implements Iterable<Set<List<Integer>>> {
	
	private final Set<List<List<Integer>>> cartProd;
	
	public RelationGenerator(int arity, int number) {
		TupleGenerator tupleGenerator = new TupleGenerator(arity);	
		Set<List<Integer>> powerSet = new HashSet<>();
		for (List<Integer> l : tupleGenerator) {
			powerSet.add(new ArrayList<>(l));
		}
		
		List<Set<List<Integer>>> sets = new ArrayList<>();
		for (int i = 0; i < number; i++) sets.add(powerSet);
		cartProd = Sets.cartesianProduct(sets);
	}

	@Override
	public Iterator<Set<List<Integer>>> iterator() {
		return new RelationGeneratorIterator(cartProd.iterator());
	}

}
