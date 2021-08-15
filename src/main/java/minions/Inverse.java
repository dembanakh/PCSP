package minions;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Inverse extends GenerativeMinion {
	
	private final GenerativeMinion minion;
	
	public Inverse(GenerativeMinion originalMinion) {
		this.minion = originalMinion;
		this.type = this.minion.type;
	}

	@Override
	public int f(int... x) {
		return 1 - minion.f(x);
	}

	@Override
	public Set<List<Integer>> applyTo(Collection<List<Integer>> relation) {
		return minion.applyTo(relation);
	}

}
