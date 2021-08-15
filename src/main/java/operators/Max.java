package operators;

import java.util.Comparator;
import java.util.List;

public class Max extends SimpleOperator {

	@Override
	public int applyOp(List<Integer> x) {
		if (x == null || x.size() != 3) throw new RuntimeException("Bad input to Max.applyOp function!");
		
		return x.stream().max(Comparator.naturalOrder()).get();
	}

}
