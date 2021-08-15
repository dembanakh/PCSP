package operators;

import java.util.Comparator;
import java.util.List;

public class Min extends SimpleOperator {

	@Override
	public int applyOp(List<Integer> x) {
		if (x == null || x.size() != 3) throw new RuntimeException("Bad input to Min.applyOp function!");
		
		return x.stream().min(Comparator.naturalOrder()).get();
	}

}
