package task1;

import operators.IOperator;
import utils.Permutator;
import utils.Util;
import utils.Util_ST_AT;

import java.util.*;

public class Task1 {

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
        int candidatesCount = 0;

        for (IOperator function : IdempotentFoldedBooleanFunction.iterateAll(arity)) {
            if (Util.Function.fullEssentialArity(function) &&
                    Util_ST_AT.negativeCoordinates(function, 0, 1, 2) &&
                    Util_ST_AT.positiveCoordinates(function, 3, 4, 5) &&
                    !Util_ST_AT.hasCoordinatesPair(function)) {
                boolean generatesConstant = false;
                Iterable<List<List<Integer>>> permutator_1 = new Permutator(new ArrayList<>(relation_1), arity);
                for (List<List<Integer>> l : permutator_1) {
                    List<Integer> evaluation = function.applyPoly(l);
                    if (Util.Relation.Tuple.isConstant(evaluation)) {
                        generatesConstant = true;
                        break;
                    }
                }
                if (!generatesConstant) {
                    Iterable<List<List<Integer>>> permutator_2 = new Permutator(new ArrayList<>(relation_2), arity);
                    for (List<List<Integer>> l : permutator_2) {
                        List<Integer> evaluation = function.applyPoly(l);
                        if (Util.Relation.Tuple.isConstant(evaluation)) {
                            generatesConstant = true;
                            break;
                        }
                    }
                    if (!generatesConstant) {
                        System.out.println(function);
                        candidatesCount++;
                    }
                }
            }
        }
        System.out.println(candidatesCount);

        // get all folded functions of arity *arity*
        // filter those that generate 0 or 1 on *relation*
    }

}
