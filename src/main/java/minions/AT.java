package minions;

import java.util.*;

import utils.Util;

public class AT extends GenerativeMinion {
	
	public AT() {
		this.type = Type.ODD;
	}

	@Override
	public int f(int... x) {
		int arity = x.length;
		if (arity % 2 == 0) throw new RuntimeException("AT generating function takes odd number of parameters!");
		
		int sum = x[0];
		for (int i = 1; i < arity; i+=2) {
			if (x[i] == 1) {
				sum -= 1; 
			}
			if (x[i+1] == 1) {
				sum += 1;
			}
		}
		
		return (sum > 0) ? 1 : 0;
	}

	@Override
	public Set<List<Integer>> applyTo(Collection<List<Integer>> relation) {
		HashSet<List<Integer>> result = new HashSet<>(Util.randomizedRelationGenerate(this, new HashSet<>(relation),
				(1 << relation.size()) + 1, 5000, false));
		result.addAll(relation);
		return result;
	}

}
