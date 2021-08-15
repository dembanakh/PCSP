package main;

import task1.IdempotentFoldedBooleanFunction;
import utils.Permutator;
import utils.Util;

import java.util.*;

public class Task3 {

    public static void main(String[] args) {
        Set<List<Integer>> relation_1 = new HashSet<>();
        relation_1.add(Arrays.asList(0, 1, 0, 1, 0, 1));
        relation_1.add(Arrays.asList(1, 0, 1, 0, 1, 0));
        relation_1.add(Arrays.asList(0, 0, 0, 0, 1, 1));
        relation_1.add(Arrays.asList(0, 0, 1, 1, 0, 0));
        relation_1.add(Arrays.asList(1, 1, 0, 0, 0, 0));

        Set<List<Integer>> relation_2 = new HashSet<>();
        relation_2.add(Arrays.asList(1, 1, 0, 0));
        relation_2.add(Arrays.asList(1, 0, 1, 0));
        relation_2.add(Arrays.asList(1, 0, 0, 1));
        relation_2.add(Arrays.asList(0, 1, 1, 0));
        relation_2.add(Arrays.asList(0, 1, 0, 1));
        relation_2.add(Arrays.asList(0, 0, 1, 1));

        int arity = 6;

        IdempotentFoldedBooleanFunction function = new IdempotentFoldedBooleanFunction(6,
                Arrays.asList(
                        1, 1, 1, 1, 1, 1, 1, 0,
                        1, 1, 1, 1, 1, 1, 1, 0,
                        0, 0, 1, 1, 1, 1, 1, 0,
                        0, 0, 1, 0, 1, 0, 1));
        if (Util.Function.fullEssentialArity(function)) {
            boolean generatesConstant = false;
            Iterable<List<List<Integer>>> permutator_1 = new Permutator(new ArrayList<>(relation_1), arity);
            for (List<List<Integer>> l : permutator_1) {
                List<Integer> evaluation = function.applyPoly(l);
                if (Util.Relation.Tuple.isConstant(evaluation)) {
                    generatesConstant = true;
                    System.out.println(l);
                    break;
                }
            }
            if (!generatesConstant) {
                Iterable<List<List<Integer>>> permutator_2 = new Permutator(new ArrayList<>(relation_2), arity);
                for (List<List<Integer>> l : permutator_2) {
                    List<Integer> evaluation = function.applyPoly(l);
                    if (Util.Relation.Tuple.isConstant(evaluation)) {
                        generatesConstant = true;
                        System.out.println(l);
                        break;
                    }
                }
                if (!generatesConstant) {
                    System.out.println(function);
                }
            }
        }
    }

}
