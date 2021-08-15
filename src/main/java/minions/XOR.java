package minions;

import utils.Util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XOR extends GenerativeMinion {

	@Override
	public int f(int... x) {
		int sum = 0;
		for (int b : x) sum += b;
		
		return sum % 2;
	}

	@Override
	public Set<List<Integer>> applyTo(Collection<List<Integer>> relation) {
		HashSet<List<Integer>> result = new HashSet<>(Util.randomizedRelationGenerate(this, new HashSet<>(relation),
				(1 << relation.size()) + 1, 5000, false));
		result.addAll(relation);
		return result;
	}

}
