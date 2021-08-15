package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import minions.AT;
import minions.GenerativeMinion;
import minions.ST;
import operators.IOperator;

public class Util_ST_AT {
	
	public static List<Set<List<Integer>>> findFirstRandomized(int relationArity, int number, int iter) {
		GenerativeMinion minionST = new ST();
		GenerativeMinion minionAT = new AT();
		
		RelationGenerator generator = new RelationGenerator(relationArity, number);
		for (Set<List<Integer>> relation : generator) {
			//if (Util.isInP(relation, iter)) continue;
			int k = (1 << number) + 1;
			Set<List<Integer>> genST = Util.randomizedRelationGenerate(minionST, relation, k, iter, false);
			Set<List<Integer>> genAT = Util.randomizedRelationGenerate(minionAT, relation, k, iter, false);
			if (!Util.compatibleRelation(genST, genAT)) {
				List<Set<List<Integer>>> result = new ArrayList<>();
				result.add(relation);
				result.add(genST);
				result.add(genAT);
				return result;
			}
		}
		return null;
	}
	
	public static List<Set<List<Integer>>> findFirstDeterministic(int relationArity, int number) {
		GenerativeMinion minionST = new ST();
		GenerativeMinion minionAT = new AT();
		
		RelationGenerator generator = new RelationGenerator(relationArity, number);
		for (Set<List<Integer>> relation : generator) {
			//if (Util.isInP(relation, iter)) continue;
			Set<List<Integer>> genST = minionST.applyTo(relation);
			Set<List<Integer>> genAT = minionAT.applyTo(relation);
			if (!Util.compatibleRelation(genST, genAT)) {
				List<Set<List<Integer>>> result = new ArrayList<>();
				result.add(relation);
				result.add(genST);
				result.add(genAT);
				return result;
			}
		}
		return null;
	}

	public static boolean hasCoordinatesPair(IOperator function) {
		int arity = function.getArity();
		for (int i = 0; i < arity; i++) {
			for (int j = 0; j < arity; j++) {
				if (i == j) continue;
				boolean bad = false;
				if (i > j) {
					for (List<Integer> tuple : new TupleGenerator(arity - 2)) {
						List<Integer> args = new ArrayList<>(tuple);
						args.add(j, 1);
						args.add(i, 0);
						if (function.applyOp(args) == 0) {
							bad = true;
							break;
						}
					}
				} else {
					for (List<Integer> tuple : new TupleGenerator(arity - 2)) {
						List<Integer> args = new ArrayList<>(tuple);
						args.add(i, 0);
						args.add(j, 1);
						if (function.applyOp(args) == 0) {
							bad = true;
							break;
						}
					}
				}

				if (!bad) return true;
			}
		}
		return false;
	}

	public static boolean negativeCoordinates(IOperator function, int... coordinates) {
		int arity = function.getArity();
		for (int c : coordinates) {
			for (List<Integer> tuple : new TupleGenerator(arity - 1)) {
				List<Integer> args = new ArrayList<>(tuple);
				args.add(c, 0);
				int value1 = function.applyOp(args);
				args.set(c, 1);
				int value2 = function.applyOp(args);
				if (value1 < value2) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean positiveCoordinates(IOperator function, int... coordinates) {
		int arity = function.getArity();
		for (int c : coordinates) {
			for (List<Integer> tuple : new TupleGenerator(arity - 1)) {
				List<Integer> args = new ArrayList<>(tuple);
				args.add(c, 0);
				int value1 = function.applyOp(args);
				args.set(c, 1);
				int value2 = function.applyOp(args);
				if (value1 > value2) {
					return false;
				}
			}
		}
		return true;
	}

}
