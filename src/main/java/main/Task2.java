package main;

import minions.GenerativeMinion;
import minions.ST;
import operators.SimpleOperator;
import utils.RelationGenerator;
import utils.Util;

import java.util.*;

public class Task2 {

    public static void main(String[] args) {
        int relationArity = 3;
        int relationSize = 6;
        int numOfIterations = 3000;

        List<Set<List<Integer>>> witness = findFirstRandomized(relationArity, relationSize, numOfIterations);
        if (witness != null) {
            System.out.println("Relation: " + witness.get(0));
            System.out.println("GenST: " + witness.get(1));
            System.out.println("GenAT: " + witness.get(2));
        }
    }

    public static List<Set<List<Integer>>> findFirstRandomized(int relationArity, int number, int iter) {
        SimpleOperator op = new SimpleOperator(6) {
            @Override
            public int applyOp(List<Integer> x) {
                if (x.get(0) == 1 && x.get(1) == 1) return 1;
                if (x.get(0) == 1) {
                    if (x.get(2) + x.get(3) + x.get(4) + x.get(5) < 2) return 1;
                    if (x.get(2) + x.get(5) == 1 && x.get(3) + x.get(4) == 1) return 1;
                }
                if (x.get(1) == 1) {
                    if (x.get(2) + x.get(3) + x.get(4) + x.get(5) < 2) return 1;
                    if (x.get(2).equals(x.get(5)) && x.get(3).equals(x.get(4)) && x.get(2) + x.get(3) == 1) return 1;
                }
                return 0;
            }
        };
        GenerativeMinion minion = new ST();

        RelationGenerator generator = new RelationGenerator(relationArity, number);
        for (Set<List<Integer>> relation : generator) {
            Set<List<Integer>> genOp = Util.randomizedSimpleOpGenerate(op, relation, iter, false);
            Set<List<Integer>> genST = minion.applyTo(relation);
            if (!Util.compatibleRelation(genST, genOp)) {
                List<Set<List<Integer>>> result = new ArrayList<>();
                result.add(relation);
                result.add(genST);
                result.add(genOp);
                return result;
            }
        }
        return null;
    }

}
