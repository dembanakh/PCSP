package main;

import minions.AT;
import minions.XOR;
import operators.SimpleOperator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task4 {

    public static void main(String[] args) {
        Set<List<Integer>> relation = new HashSet<>();
        relation.add(Arrays.asList(0, 1, 0, 1, 0, 1));
        relation.add(Arrays.asList(1, 0, 1, 0, 1, 0));
        relation.add(Arrays.asList(0, 0, 0, 0, 1, 1));
        relation.add(Arrays.asList(0, 0, 1, 1, 0, 0));
        relation.add(Arrays.asList(1, 1, 0, 0, 0, 0));

        Set<List<Integer>> result =
                new SimpleOperator(6) {
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
        }
        //new XOR()
        .applyTo(relation);
        System.out.println(result.size());
        for (List<Integer> r : result) {
            System.out.println(r);
        }
    }

}
