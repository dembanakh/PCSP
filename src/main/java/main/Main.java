package main;

import java.util.List;
import java.util.Map;
import java.util.Set;

import minions.AT;
import minions.GenerativeMinion;
import minions.Inverse;
import minions.ST;
import minions.THR;
import minions.XOR;
import operators.SimpleOperator;
import utils.Util;
import utils.Util_ST_AT;

public class Main {
	
	public static void main(String[] args) {
		int relationArity = 4;
		int relationSize = 5;
		int numOfIterations = 5000;
		
		//List<List<List<Integer>>> witness = Util_ST_AT.findFirstRandomized(relationArity, relationSize, numOfIterations);
		List<Set<List<Integer>>> witness = Util_ST_AT.findFirstDeterministic(relationArity, relationSize);
		if (witness != null) {
			System.out.println("Relation: " + witness.get(0));
			System.out.println("GenST: " + witness.get(1));
			System.out.println("GenAT: " + witness.get(2));
		}
	}
	
	private static void oldMain() {
		int relation_arity = 5;
		int relation_size = 5;
		int numOfIterations = 300;
		
		GenerativeMinion minionST = new ST();
		GenerativeMinion minionAT = new AT();
		/*SimpleOperator minionST = new SimpleOperator(5) {
			@Override
			public int applyOp(List<Integer> x) {
				return (x.get(0)*(1) + x.get(1)*(2) + x.get(2)*(-2) + x.get(3)*(4) + x.get(4)*(-4)) > 0 ? 1 : 0;
			}
		};*/
		/*SimpleOperator minionAT = new SimpleOperator(5) {
			@Override
			public int applyOp(List<Integer> x) {
				return (x.get(0)*(1) + x.get(1)*(-1) + x.get(2)*(1) + x.get(3)*(-1) + x.get(4)*(1)) > 0 ? 1 : 0;
			}
		};*/
		/*
		GenerativeMinion minionXOR = new XOR();
		GenerativeMinion minionTHR_1 = new THR(1, 4);
		GenerativeMinion minionTHR_2 = new THR(2, 4);
		GenerativeMinion minionTHR_3 = new THR(3, 4);
		GenerativeMinion inverseAT = new Inverse(minionAT);
		GenerativeMinion inverseXOR = new Inverse(minionXOR);
		GenerativeMinion inverseTHR_1 = new Inverse(minionTHR_1);
		GenerativeMinion inverseTHR_2 = new Inverse(minionTHR_2);
		GenerativeMinion inverseTHR_3 = new Inverse(minionTHR_3);
		*/

		Map<Set<List<Integer>>, List<List<Integer>>> resultsST =
				Util.randomizedRelationGenerateParallel(minionST, relation_arity, relation_size, numOfIterations);
		Map<Set<List<Integer>>, List<List<Integer>>> resultsAT =
				Util.randomizedRelationGenerateParallel(minionAT, relation_arity, relation_size, numOfIterations);
		//Map<List<List<Integer>>, List<List<Integer>>> resultsOp =
		//		Util.randomizedSimpleOpGenerate(op, relation_arity, relation_size, numOfIterations);
		/*Map<List<List<Integer>>, Map<Integer, List<List<Integer>>>> resultsXOR = 
				Util.randomizedRelationGenerate(minionXOR, relation_arity, relation_size, numOfIterations);
		Map<List<List<Integer>>, Map<Integer, List<List<Integer>>>> resultsTHR_1 = 
				Util.randomizedRelationGenerate(minionTHR_1, relation_arity, relation_size, numOfIterations);
		Map<List<List<Integer>>, Map<Integer, List<List<Integer>>>> resultsTHR_2 = 
				Util.randomizedRelationGenerate(minionTHR_2, relation_arity, relation_size, numOfIterations);
		Map<List<List<Integer>>, Map<Integer, List<List<Integer>>>> resultsTHR_3 = 
				Util.randomizedRelationGenerate(minionTHR_3, relation_arity, relation_size, numOfIterations);
		Map<List<List<Integer>>, Map<Integer, List<List<Integer>>>> resultsInvAT = 
				Util.randomizedRelationGenerate(inverseAT, relation_arity, relation_size, numOfIterations);
		Map<List<List<Integer>>, Map<Integer, List<List<Integer>>>> resultsInvXOR = 
				Util.randomizedRelationGenerate(inverseXOR, relation_arity, relation_size, numOfIterations);
		Map<List<List<Integer>>, Map<Integer, List<List<Integer>>>> resultsInvTHR_1 = 
				Util.randomizedRelationGenerate(inverseTHR_1, relation_arity, relation_size, numOfIterations);
		Map<List<List<Integer>>, Map<Integer, List<List<Integer>>>> resultsInvTHR_2 = 
				Util.randomizedRelationGenerate(inverseTHR_2, relation_arity, relation_size, numOfIterations);
		Map<List<List<Integer>>, Map<Integer, List<List<Integer>>>> resultsInvTHR_3 = 
				Util.randomizedRelationGenerate(inverseTHR_3, relation_arity, relation_size, numOfIterations);
		*/
		for (Set<List<Integer>> key : resultsST.keySet()) {
			List<List<Integer>> mapST = resultsST.get(key);
			List<List<Integer>> mapAT = resultsAT.get(key);
			//List<List<Integer>> mapOp = resultsOp.get(key);
			/*Map<Integer, List<List<Integer>>> mapXOR = resultsXOR.get(key);
			Map<Integer, List<List<Integer>>> mapTHR_1 = resultsTHR_1.get(key);
			Map<Integer, List<List<Integer>>> mapTHR_2 = resultsTHR_2.get(key);
			Map<Integer, List<List<Integer>>> mapTHR_3 = resultsTHR_3.get(key);
			Map<Integer, List<List<Integer>>> mapInvAT = resultsInvAT.get(key);
			Map<Integer, List<List<Integer>>> mapInvXOR = resultsInvXOR.get(key);
			Map<Integer, List<List<Integer>>> mapInvTHR_1 = resultsInvTHR_1.get(key);
			Map<Integer, List<List<Integer>>> mapInvTHR_2 = resultsInvTHR_2.get(key);
			Map<Integer, List<List<Integer>>> mapInvTHR_3 = resultsInvTHR_3.get(key);
			*/
			if (!mapST.isEmpty()) {
				/*
				if (Util.compatibleRelation(mapST, mapAT)) continue;
				if (Util.compatibleRelation(mapST, mapXOR)) continue;
				if (Util.compatibleRelation(mapST, mapTHR_1)) continue;
				if (Util.compatibleRelation(mapST, mapTHR_2)) continue;
				if (Util.compatibleRelation(mapST, mapTHR_3)) continue;
				if (Util.compatibleRelation(mapST, mapInvAT)) continue;
				if (Util.compatibleRelation(mapST, mapInvXOR)) continue;
				if (Util.compatibleRelation(mapST, mapInvTHR_1)) continue;
				if (Util.compatibleRelation(mapST, mapInvTHR_2)) continue;
				if (Util.compatibleRelation(mapST, mapInvTHR_3)) continue;
				*/
				if (!Util.compatibleRelation(mapST, mapAT)) {
					System.out.println(key + ": " + mapST + " " + mapAT);
					break;
				}
			}
		}
	}

}
