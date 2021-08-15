package minions;

import java.util.*;

import operators.SimpleOperator;

public class ST extends GenerativeMinion {
	
	public ST() {
		super(Type.ODD);
	}
	
	/*
	 * Generating function for ST minion.
	 */
	@Override
	public int f(int... x) {
		int arity = x.length;
		if (arity % 2 == 0) throw new RuntimeException("ST generating function takes odd number of parameters!");
		
		int sum = x[0];
		int power = 2;
		for (int i = 1; i < arity; i+=2) {
			if (x[i] == 1) {
				sum += power; 
			}
			if (x[i+1] == 1) {
				sum -= power;
			}
			power *= 2;
		}
		
		return (sum > 0) ? 1 : 0;
	}

	@Override
	public Set<List<Integer>> applyTo(Collection<List<Integer>> relation) {
		Set<List<Integer>> result = new HashSet<>(relation);
		SimpleOperator st3 = new SimpleOperator(3) {
			@Override
			public int applyOp(List<Integer> x) {
				return x.get(0) + 2 * x.get(1) - 2 * x.get(2) > 0 ? 1 : 0;
			}
		};
		
		int prevSize;
		do {
			prevSize = result.size();
			Set<List<Integer>> tempResult = new HashSet<>();
			for (List<Integer> r : result) {
				for (List<Integer> s : relation) {
					for (List<Integer> t : relation) {
						tempResult.add(st3.applyPoly(Arrays.asList(r, s, t)));
					}
				}
			}
			result.addAll(tempResult);
		} while (prevSize != result.size());
		
		return result;
	}

}
