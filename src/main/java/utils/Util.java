package utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import minions.GenerativeMinion;
import minions.GenerativeMinion.Type;
import minions.ST;
import operators.*;

public class Util {
	
	private Util() {}
	
	/*
	 * @return Returns true if @param mapSec is a subset of @param mapMain in the sense of Set<List<Integer>>
	 */
	public static boolean compatibleRelation(List<List<Integer>> mapMain, List<List<Integer>> mapSec) {
		if (mapSec != null && !mapSec.isEmpty()) {
			Set<List<Integer>> unionMain = new HashSet<>(mapMain);
			Set<List<Integer>> unionSec = new HashSet<>(mapSec);
			return unionMain.containsAll(unionSec);
		}
		return true;
	}
	
	public static boolean compatibleRelation(Set<List<Integer>> mapMain, Set<List<Integer>> mapSec) {
		if (mapSec != null) {
			return mapMain.containsAll(mapSec);
		}
		return true;
	}
	
	/*
	 * @param minion - set of generating functions
	 * Set s - relation, k - arity of ST generating function, n - number of random iterations
	 * Returns List of tuples, that ST generates from s, and that are not included in s.
	 */
	public static Set<List<Integer>> randomizedRelationGenerate(GenerativeMinion minion, Set<List<Integer>> s,
			int k, int n, boolean verbose) {
		//if (s == null || s.size() == 0) throw new RuntimeException("Bad input to randomizedRelationGenerate");
		
		Set<List<Integer>> generations = new HashSet<>();
		RandomPermutator permutator = new RandomPermutator(new ArrayList<>(s), k, n);
		for (List<List<Integer>> l : permutator) {
			List<Integer> evaluation = minion.f(l);
			if (!s.contains(evaluation)) {
				generations.add(evaluation);
				if (verbose) {
					System.out.println(l);
					System.out.println(evaluation);
				}
			}
		}
		
		return generations;
	}

	public static List<List<Integer>> randomizedRelationGenerateParallel(GenerativeMinion minion, List<List<Integer>> s,
			int k, int n, boolean verbose) {
		if (s == null || s.size() == 0) throw new RuntimeException("Bad input to randomizedRelationGenerate");
		
		ExecutorService executor = Executors.newCachedThreadPool();
		List<Future<List<Integer>>> generations = new ArrayList<>();
		RandomPermutator permutator = new RandomPermutator(s, k, n);
		for (List<List<Integer>> l : permutator) {
			List<List<Integer>> copyL = new ArrayList<>(l);
			generations.add(executor.submit(() -> {
				List<Integer> evaluation = minion.f(copyL);
				if (!s.contains(evaluation)) {
					if (verbose) {
						System.out.println(copyL);
						System.out.println(evaluation);
					}
					return evaluation;
				}
				return null;
			}));
		}
		
		executor.shutdown();
		
		return generations.stream().map(future -> {
			try {
				return future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}).filter(Objects::nonNull).distinct().collect(Collectors.toList());
	}
	
	public static List<List<List<Integer>>> fullLastCoordinateRelationGenerate(GenerativeMinion minion, List<List<Integer>> s,
			int k, int n, boolean verbose) {
		if (s == null || s.size() == 0) throw new RuntimeException("Bad input to fullLastCoordinateRelationGenerate");
		
		int arity = s.get(0).size();
		List<List<List<Integer>>> generations = new ArrayList<>();
		TupleGenerator lastCoordGenerator = new TupleGenerator(s.size());
		for (List<Integer> lastCoord : lastCoordGenerator) {
			for (int i = 0; i < s.size(); i++) s.get(i).set(arity - 1, lastCoord.get(i));
			if (verbose) {
				System.out.println(lastCoord + ":");
			}
			
			List<List<Integer>> localGenerations = new ArrayList<>();
			RandomPermutator permutator = new RandomPermutator(s, k, n);
			for (List<List<Integer>> l : permutator) {
				List<Integer> evaluation = minion.f(l);
				if (!s.contains(evaluation) && !localGenerations.contains(evaluation)) {
					localGenerations.add(evaluation);
					if (verbose) {
						System.out.println(l);
						System.out.println(evaluation);
					}
				}
			}
			generations.add(localGenerations);
		}
		
		return generations;
	}
	
	public static Map<List<List<Integer>>, List<List<Integer>>> randomizedRelationGenerate(
			GenerativeMinion minion, int relation_arity, int number, int iter) {
		Map<List<List<Integer>>, List<List<Integer>>> results = new HashMap<>();
		
		RelationGenerator generator = new RelationGenerator(relation_arity, number);
		for (Set<List<Integer>> relation : generator) {
			if (Util.isInP(relation, iter)) continue;
			List<List<Integer>> map = new ArrayList<>();
			for (int k = (minion.type == Type.ODD) ? 3 : 2; k <= 2*number+3; k += (minion.type == Type.ALL) ? 1 : 2) {
				Set<List<Integer>> gen = Util.randomizedRelationGenerate(minion, relation, k, iter, false);
				boolean toContinue = minion.getClass() != ST.class;
				for (List<Integer> l : gen) {
					if (!map.contains(l)) {
						toContinue = true;
						map.add(l);
					}
				}
				if (!toContinue) break;
			}
			Set<List<Integer>> temporary = new HashSet<>(relation);
			temporary.addAll(map);
			if (!Util.isInP(temporary, iter)) results.put(new ArrayList<>(relation), map);
		}
		
		return results;
	}
	
	public static Map<Set<List<Integer>>, Set<List<Integer>>> deterministicRelationGenerate(
			GenerativeMinion minion, int relation_arity, int number) {
		Map<Set<List<Integer>>, Set<List<Integer>>> results = new HashMap<>();
		
		RelationGenerator generator = new RelationGenerator(relation_arity, number);
		for (Set<List<Integer>> relation : generator) {
			results.put(new HashSet<>(relation), minion.applyTo(relation));
		}
		
		return results;
	}
	
	public static Map<Set<List<Integer>>, List<List<Integer>>> randomizedRelationGenerateParallel(GenerativeMinion minion, int relation_arity, int number, int iter) {
		Map<Set<List<Integer>>, List<List<Integer>>> results = new ConcurrentHashMap<>();
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		Map<Set<List<Integer>>, Future<List<List<Integer>>>> concResults = new HashMap<>();
		RelationGenerator generator = new RelationGenerator(relation_arity, number);
		for (Set<List<Integer>> relation : generator) {
			Set<List<Integer>> copyRelation = new HashSet<>(relation);
			concResults.put(copyRelation, executor.submit(() -> {
				List<List<Integer>> map = new ArrayList<>();
				if (Util.isInP(copyRelation, iter)) return map;
				for (int k = (minion.type == Type.ODD) ? 3 : 2;
						k <= 2 * number + 3;
						k += (minion.type == Type.ALL) ? 1 : 2) {
					Set<List<Integer>> gen = Util.randomizedRelationGenerate(minion, copyRelation, k, iter, false);
					for (List<Integer> l : gen) {
						if (!map.contains(l)) map.add(l);
					}
				}

				Set<List<Integer>> temporary = new HashSet<>(copyRelation);
				temporary.addAll(map);
				if (!Util.isInP(temporary, iter)) return map;
				return new ArrayList<>();
			}));
		}
		
		for (Set<List<Integer>> key : concResults.keySet()) {
			Future<List<List<Integer>>> result = concResults.get(key);
			try {
				results.put(key, result.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		executor.shutdown();
		
		return results;
	}
	
	public static Map<List<List<Integer>>, List<List<Integer>>> randomizedSimpleOpGenerate(IOperator op, int relation_arity, int number, int iter) {
		Map<List<List<Integer>>, List<List<Integer>>> results = new HashMap<>();
		
		RelationGenerator generator = new RelationGenerator(relation_arity, number);
		for (Set<List<Integer>> relation : generator) {
			if (Util.isInP(relation, iter)) continue;
			List<List<Integer>> map = new ArrayList<>(Util.randomizedSimpleOpGenerate(op, relation, iter, false));
			results.put(new ArrayList<>(relation), map);
		}
		
		return results;
	}
	
	public static Set<List<Integer>> randomizedSimpleOpGenerate(IOperator op, Collection<List<Integer>> relation, int iter, boolean verbose) {
		Set<List<Integer>> generations = new HashSet<>();
		Iterable<List<List<Integer>>> permutator = new RandomPermutator(new ArrayList<>(relation), op.getArity(), iter);
		for (List<List<Integer>> l : permutator) {
			List<Integer> evaluation = op.applyPoly(l);
			if (!relation.contains(evaluation)) {
				generations.add(evaluation);
				if (verbose) {
					System.out.println(l);
					System.out.println(evaluation);
				}
			}
		}
		
		return generations;
	}

	public static List<List<Integer>> simpleOpGenerate(IOperator op, Set<List<Integer>> relation, boolean verbose) {
		List<List<Integer>> generations = new ArrayList<>();
		Iterable<List<List<Integer>>> permutator = new Permutator(new ArrayList<>(relation), op.getArity());
		for (List<List<Integer>> l : permutator) {
			List<Integer> evaluation = op.applyPoly(l);
			if (!relation.contains(evaluation) && !generations.contains(evaluation)) {
				generations.add(evaluation);
				if (verbose) {
					System.out.println(l);
					System.out.println(evaluation);
				}
			}
		}

		return generations;
	}
	
	public static boolean isInP(Set<List<Integer>> relation, int iter) {
		if (Util.hasSameHammingWeight(relation)) return true;
		
		boolean majority_generatedSthElse = !Util.randomizedSimpleOpGenerate(new Majority(), relation, iter, false).isEmpty();
		boolean minority_generatedSthElse = !Util.randomizedSimpleOpGenerate(new Minority(), relation, iter, false).isEmpty();
		boolean min_generatedSthElse = !Util.randomizedSimpleOpGenerate(new Min(), relation, iter, false).isEmpty();
		boolean max_generatedSthElse = !Util.randomizedSimpleOpGenerate(new Max(), relation, iter, false).isEmpty();
		return !max_generatedSthElse || !min_generatedSthElse
				|| !majority_generatedSthElse || !minority_generatedSthElse;
	}
	
	public static boolean hasSameHammingWeight(Set<List<Integer>> relation) {
		if (relation == null || relation.size() == 0) throw new RuntimeException("Bad input to hasSameHammingWeight!");
		
		int weight = -1;
		for (List<Integer> tuple : relation) {
			if (weight == -1) {
				weight = Util.hammingWeight(tuple);
				continue;
			}
			if (Util.hammingWeight(tuple) != weight) return false;
		}
		
		return true;
	}
	
	public static int hammingWeight(List<Integer> relation) {
		int weight = 0;
		for (Integer i : relation) {
			if (i.equals(1)) ++weight;
		}
		return weight;
	}

	public static class Function {

		public static boolean fullEssentialArity(IOperator function) {
			for (int i = 0; i < function.getArity(); i++) {
				if (!essentialCoordinate(i, function)) return false;
			}
			return true;
		}

		public static boolean essentialCoordinate(int index, IOperator function) {
			for (List<Integer> tuple : new TupleGenerator(function.getArity() - 1)) {
				List<Integer> args = new ArrayList<>(tuple);
				args.add(index, 0);
				int value0 = function.applyOp(args);
				args.set(index, 1);
				int value1 = function.applyOp(args);
				if (value0 != value1) return true;
			}
			return false;
		}

		public static boolean eitherToneCoordinate(int index, IOperator function) {
			boolean monotone = true;
			boolean antitone = true;
			for (List<Integer> tuple : new TupleGenerator(function.getArity() - 1)) {
				List<Integer> args = new ArrayList<>(tuple);
				args.add(index, 0);
				int value0 = function.applyOp(args);
				args.set(index, 1);
				int value1 = function.applyOp(args);
				if (value0 > value1) monotone = false;
				if (value0 < value1) antitone = false;
				if (!monotone && !antitone) return false;
			}
			return true;
		}

		public static boolean isEitherTone(IOperator function) {
			for (int i = 0; i < function.getArity(); i++) {
				if (!eitherToneCoordinate(i, function)) return false;
			}
			return true;
		}

		public static boolean isHET(IOperator function) {
			if (function.getArity() == 1) return true;
			if (function.getArity() == 2) {
				int value00 = function.applyOp(Arrays.asList(0, 0));
				int value01 = function.applyOp(Arrays.asList(0, 1));
				int value10 = function.applyOp(Arrays.asList(1, 0));
				int value11 = function.applyOp(Arrays.asList(1, 1));
				return value00 != value11 || value01 != value10 || value00 == value01;
			}
			// check if function is ET
			// check if minors of arity >= 2 are HET
			return isEitherTone(function);
		}

	}

	public static class Relation {

		public static Collection<List<Integer>> project(Collection<List<Integer>> set, List<Integer> coords) {
			Collection<List<Integer>> result = new HashSet<>();
			for (List<Integer> tuple : set) {
				result.add(Tuple.project(tuple, coords));
			}
			return result;
		}

		public static class Tuple {

			public static List<Integer> project(List<Integer> tuple, List<Integer> coords) {
				List<Integer> result = new ArrayList<>();
				for (int c : coords) {
					result.add(tuple.get(c));
				}
				return result;
			}

			public static boolean isConstant(List<Integer> tuple) {
				int value = tuple.get(0);
				for (Integer v : tuple) {
					if (v != value) return false;
				}
				return true;
			}

		}

	}

}
