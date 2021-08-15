package operators;

import java.util.List;

public class Majority extends SimpleOperator {
	
	@Override
	public int applyOp(List<Integer> x) {
		if (x == null || x.size() != 3) throw new RuntimeException("Bad input to Majority.applyOp function!");
		
		if (x.get(0) == 1) {
			if (x.get(1) == 1) return 1;
			else if (x.get(2) == 1) return 1;
			else return 0;
		} else {
			if (x.get(1) == 0) return 0;
			else if (x.get(2) == 0) return 0;
			return 1;
		}
	}
	
}
