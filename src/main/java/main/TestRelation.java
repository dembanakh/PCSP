package main;

import java.util.*;

import minions.AT;
import minions.GenerativeMinion;
import minions.ST;
import minions.XOR;
import operators.IOperator;
import operators.SimpleOperator;
import utils.Util;

public class TestRelation {
	
	public static void main(String[] args) {
		Set<List<Integer>> relation = new HashSet<>();
		relation.add(Arrays.asList(0, 0, 1));
		relation.add(Arrays.asList(0, 1, 0));
		relation.add(Arrays.asList(0, 1, 1));
		relation.add(Arrays.asList(1, 1 ,0));
		relation.add(Arrays.asList(0, 0, 0));

		relation_compare(relation, new XOR(), new AT());
	}

	private static void main_spec() {
		Set<List<Integer>> relation = new HashSet<>();

		// {x \in (2^5)^T | 0 < H(x) < 3}
		/*relation.add(Arrays.asList(0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1));
		relation.add(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1));
		relation.add(Arrays.asList(0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0));
		relation.add(Arrays.asList(0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0));
		relation.add(Arrays.asList(1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0));*/

		// {x \in (2^5)^T | H(x) = 2}
		/*relation.add(Arrays.asList(0, 0, 0, 1, 0, 0, 1, 0, 1, 1));
		relation.add(Arrays.asList(0, 0, 1, 0, 0, 1, 0, 1, 0, 1));
		relation.add(Arrays.asList(0, 1, 0, 0, 1, 0, 0, 1, 1, 0));
		relation.add(Arrays.asList(1, 0, 0, 0, 1, 1, 1, 0, 0, 0));
		relation.add(Arrays.asList(1, 1, 1, 1, 0, 0, 0, 0, 0, 0));*/

		// {x \in (2^5)^T | H(x) = 2 && (x_1 != x_2 || x_4 != x_5)}
		/*relation.add(Arrays.asList(0, 0, 1, 0, 0, 1, 0, 1));
		relation.add(Arrays.asList(0, 1, 0, 0, 1, 0, 1, 0));
		relation.add(Arrays.asList(1, 0, 0, 1, 0, 0, 1, 1));
		relation.add(Arrays.asList(0, 0, 0, 1, 1, 1, 0, 0));
		relation.add(Arrays.asList(1, 1, 1, 0, 0, 0, 0, 0));*/

		// {?}
		relation.add(Arrays.asList(0, 1, 0, 1, 0, 1));
		relation.add(Arrays.asList(1, 0, 1, 0, 1, 0));
		relation.add(Arrays.asList(0, 0, 0, 0, 1, 1));
		relation.add(Arrays.asList(0, 0, 1, 1, 0, 0));
		relation.add(Arrays.asList(1, 1, 0, 0, 0, 0));

		Set<List<Integer>> resultST = new ST().applyTo(relation);
		System.out.println(resultST.size());
		Set<List<Integer>> resultAT = new AT().applyTo(relation);
		resultAT.addAll(resultST);
		System.out.println(resultAT.size());
		resultAT.removeAll(resultST);
		for (List<Integer> r : resultAT) {
			System.out.println(r);
		}
	}

	private static void relation_compare(Set<List<Integer>> relation, SimpleOperator op, GenerativeMinion minion) {
		Set<List<Integer>> resultOp = op.applyTo(relation);
		Set<List<Integer>> resultMinion = minion.applyTo(relation);
		resultOp.removeAll(resultMinion);
		for (List<Integer> r : resultOp) {
			System.out.println(r);
		}
	}

	private static void relation_compare(Set<List<Integer>> relation, GenerativeMinion minion1, GenerativeMinion minion2) {
		Set<List<Integer>> resultMinion1 = minion1.applyTo(relation);
		Set<List<Integer>> resultMinion2 = minion2.applyTo(relation);
		resultMinion1.removeAll(resultMinion2);
		for (List<Integer> r : resultMinion1) {
			System.out.println(r);
		}
	}

	private static void main_compare(SimpleOperator op, GenerativeMinion minion) {
		Set<List<Integer>> relation = new HashSet<>();
		relation.add(Arrays.asList(0, 1, 0, 1, 0, 1));
		relation.add(Arrays.asList(1, 0, 1, 0, 1, 0));
		relation.add(Arrays.asList(0, 0, 0, 0, 1, 1));
		relation.add(Arrays.asList(0, 0, 1, 1, 0, 0));
		relation.add(Arrays.asList(1, 1, 0, 0, 0, 0));

		Set<List<Integer>> resultOp = op.applyTo(relation);
		Set<List<Integer>> resultMinion = minion.applyTo(relation);
		resultOp.removeAll(resultMinion);
		for (List<Integer> r : resultOp) {
			System.out.println(r);
		}
	}

	private static void main_single(SimpleOperator op) {
		Set<List<Integer>> relation = new HashSet<>();
		relation.add(Arrays.asList(0, 1, 0, 1, 0, 1));
		relation.add(Arrays.asList(1, 0, 1, 0, 1, 0));
		relation.add(Arrays.asList(0, 0, 0, 0, 1, 1));
		relation.add(Arrays.asList(0, 0, 1, 1, 0, 0));
		relation.add(Arrays.asList(1, 1, 0, 0, 0, 0));

		Set<List<Integer>> result = op.applyTo(relation);
		System.out.println(result.size());
		for (List<Integer> r : result) {
			System.out.println(r);
		}
	}
	
	private static void main_single(GenerativeMinion minion) {
		Set<List<Integer>> relation = new HashSet<>();
		relation.add(Arrays.asList(0, 1, 0, 1, 0, 1));
		relation.add(Arrays.asList(1, 0, 1, 0, 1, 0));
		relation.add(Arrays.asList(0, 0, 0, 0, 1, 1));
		relation.add(Arrays.asList(0, 0, 1, 1, 0, 0));
		relation.add(Arrays.asList(1, 1, 0, 0, 0, 0));

		Set<List<Integer>> result = minion.applyTo(relation);
		System.out.println(result.size());
		/*for (List<Integer> r : result) {
			System.out.println(r);
		}*/
	}
	
	private static void main_FunctionGraph3() {
		Set<List<Integer>> relation = new HashSet<>();
		relation.add(Arrays.asList(0, 0, 0));
		relation.add(Arrays.asList(0, 1, 0));
		relation.add(Arrays.asList(1, 0, 0));
		relation.add(Arrays.asList(1, 1, 1));
		
		Set<List<Integer>> result = new ST().applyTo(relation);
		System.out.println(result.size());
		for (List<Integer> l : result) {
			System.out.println(l);
		}
	}
	
	private static void main_FunctionGraph4() {
		Set<List<Integer>> relation = new HashSet<>();
		relation.add(Arrays.asList(0, 0, 0, 0));
		relation.add(Arrays.asList(0, 0, 1, 0));
		relation.add(Arrays.asList(0, 1, 0, 0));
		relation.add(Arrays.asList(0, 1, 1, 0));
		relation.add(Arrays.asList(1, 0, 0, 0));
		relation.add(Arrays.asList(1, 0, 1, 1));
		relation.add(Arrays.asList(1, 1, 0, 1));
		relation.add(Arrays.asList(1, 1, 1, 1));

		Set<List<Integer>> result = new ST().applyTo(relation);
		System.out.println(result.size());
		for (List<Integer> l : result) {
			System.out.println(l);
		}
	}
	
	private static void main_R3_single(GenerativeMinion minion) {
		Set<List<Integer>> relation = new HashSet<>();
		relation.add(Arrays.asList(0, 1, 0));
		relation.add(Arrays.asList(1, 0, 0));
		relation.add(Arrays.asList(1, 1, 1));
		
		int iter = 3000;
		
		Set<List<Integer>> result = Util.randomizedRelationGenerate(minion, relation, 5, iter, true);
		System.out.println(result.size());
		for (List<Integer> l : result) {
			System.out.println(l);
		}
	}
	
	private static void main_R4_single() {
		Set<List<Integer>> relation = new HashSet<List<Integer>>();
		relation.add(Arrays.asList(0, 0, 0, 0));
		relation.add(Arrays.asList(0, 0, 1, 1));
		relation.add(Arrays.asList(0, 1, 0, 0));
		relation.add(Arrays.asList(0, 1, 1, 0));
		relation.add(Arrays.asList(1, 0, 0, 1));
		//relation.add(Arrays.asList(1, 0, 1, 0));
		relation.add(Arrays.asList(1, 1, 0, 0));
		//relation.add(Arrays.asList(1, 1, 1, 1));
		
		int iter = 3000;
		
		Set<List<Integer>> result = Util.randomizedRelationGenerate(new ST(), relation, 5, iter, true);
		System.out.println(result.size());
		for (List<Integer> l : result) {
			System.out.println(l);
		}
	}
	
	private static void main_R5_single(GenerativeMinion minion) {
		Set<List<Integer>> relation = new HashSet<>();
		relation.add(Arrays.asList(0, 1, 1, 0, 0));
		relation.add(Arrays.asList(0, 1, 0, 1, 1));
		relation.add(Arrays.asList(1, 0, 1, 0, 0));
		relation.add(Arrays.asList(1, 1, 0, 0, 0));
		relation.add(Arrays.asList(1, 1, 0, 0, 1));
		
		int iter = 3000;
		
		Set<List<Integer>> result = Util.randomizedRelationGenerate(minion, relation, 25, iter, false);
		System.out.println(result.size());
		for (List<Integer> l : result) {
			System.out.println(l);
		}
	}
	
	private static void main_R4() {
		List<List<Integer>> relation = new ArrayList<List<Integer>>();
		relation.add(Arrays.asList(0, 0, 0, 0));
		relation.add(Arrays.asList(0, 0, 1, 0));
		relation.add(Arrays.asList(0, 1, 0, 0));
		relation.add(Arrays.asList(0, 1, 1, 0));
		relation.add(Arrays.asList(1, 0, 0, 0));
		relation.add(Arrays.asList(1, 0, 1, 0));
		relation.add(Arrays.asList(1, 1, 0, 0));
		relation.add(Arrays.asList(1, 1, 1, 0));
		
		int iter = 3000;
		
		List<List<List<Integer>>> result = Util.fullLastCoordinateRelationGenerate(new ST(), relation, 15, iter, false);
		for (List<List<Integer>> lastCoordSetup : result) {
			System.out.println(lastCoordSetup.size());
			/*for (List<Integer> l : result) {
				System.out.println(l);
			}*/
		}
	}
	
	private static void main_R3() {
		List<List<Integer>> relation = new ArrayList<List<Integer>>();
		relation.add(Arrays.asList(0, 0, 0));
		relation.add(Arrays.asList(0, 1, 0));
		relation.add(Arrays.asList(1, 0, 0));
		relation.add(Arrays.asList(1, 1, 0));
		
		int iter = 3000;
		
		List<List<List<Integer>>> result = Util.fullLastCoordinateRelationGenerate(new ST(), relation, 15, iter, false);
		for (List<List<Integer>> lastCoordSetup : result) {
			System.out.println(lastCoordSetup.size());
			/*for (List<Integer> l : lastCoordSetup) {
				System.out.println(l);
			}*/
		}
	}

}
