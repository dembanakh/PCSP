package utils;

import java.util.ArrayList;
import java.util.List;

import operators.SimpleOperator;

public class FunctionGraph {
	
	private final SimpleOperator function;
	
	public FunctionGraph(SimpleOperator function) {
		this.function = function;
	}
	
	public void apply(List<List<Integer>> domain) {
		for (List<Integer> row : domain) {
			if (row.size() != function.getArity())
				throw new RuntimeException("Bad input to FunctionGraph::apply: one of rows does not have the proper size");
		}
		
		for (int i = 0; i < domain.size(); i++) {
			List<Integer> row = new ArrayList<>(domain.get(i));
			row.add(function.applyOp(row));
			domain.set(i, row);
		}
	}

}
