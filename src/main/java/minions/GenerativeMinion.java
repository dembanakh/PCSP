package minions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class GenerativeMinion {
	
	public enum Type {
		ODD, EVEN, ALL;
	}
	
	public Type type;
	
	protected GenerativeMinion() {
		this.type = Type.ALL;
	}
	
	protected GenerativeMinion(Type type) {
		this.type = type;
	}
	
	/*
	 * Generating function for the minion.
	 */
	public abstract int f(int... x);
	
	public List<Integer> f(List<List<Integer>> input) {
		if (input == null || input.size() == 0 || input.get(0).size() == 0) 
			throw new RuntimeException("Bad input to f(List<List>) in class " + this.getClass().getSimpleName());
		
		List<Integer> eval = new ArrayList<>();
		int eval_arity = input.get(0).size();
		int f_arity = input.size();
		for (int i = 0; i < eval_arity; i++) {
			int[] x = new int[f_arity];
			for (int j = 0; j < f_arity; j++) x[j] = input.get(j).get(i);
			eval.add(this.f(x));
		}
		
		return eval;
	}
	
	public abstract Set<List<Integer>> applyTo(Collection<List<Integer>> relation);

}
