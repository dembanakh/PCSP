package operators;

import utils.Util;

import java.util.*;

public abstract class SimpleOperator implements IOperator {
	
	private final int arity;
	
	protected SimpleOperator() {
		this.arity = 3;
	}
	
	protected SimpleOperator(int arity) {
		this.arity = arity;
	}

	@Override
	public int getArity() {
		return this.arity;
	}

	public Set<List<Integer>> applyTo(Collection<List<Integer>> relation) {
		HashSet<List<Integer>> result = new HashSet<>(Util
				//.simpleOpGenerate(
				.randomizedSimpleOpGenerate(
						this, new HashSet<>(relation),
						2000,
						true));
		result.addAll(relation);
		return result;
	}

}
