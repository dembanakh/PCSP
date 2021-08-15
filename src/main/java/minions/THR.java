package minions;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class THR extends GenerativeMinion {
	
	/*
	 * q-Threshold minion, where q = m/n is a rational between 0 and 1
	 */
	
	private final int m;
	private final int n;
	
	public THR(int m, int n) {
		if (m < 0 || n < 0 || m >= n) throw new IllegalArgumentException("Construction of THR minion is corrupted due to illegal q-value!");
		this.m = m;
		this.n = n;
		
		this.type = Type.ALL;
	}

	@Override
	public int f(int... x) {
		int arity = x.length;
		
		int sum = 0;
		for (int b : x) sum += b;
		
		return (n * sum > arity * m) ? 1 : 0;
	}

	@Override
	public Set<List<Integer>> applyTo(Collection<List<Integer>> relation) {
		return null;
	}

}
