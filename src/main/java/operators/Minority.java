package operators;

import java.util.List;

public class Minority extends SimpleOperator {

	@Override
	public int applyOp(List<Integer> x) {
		if (x == null || x.size() != 3) throw new RuntimeException("Bad input to Minority.applyOp function!");
		
		return x.stream().mapToInt(i -> i).sum() % 2;
	}

}
