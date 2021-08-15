package main;

import minions.AT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskHET {

    public static void main(String[] args) {
        Set<List<Integer>> relation = new HashSet<>();
        relation.add(Arrays.asList(0, 0, 0, 0));
        relation.add(Arrays.asList(0, 0, 1, 1));
        relation.add(Arrays.asList(1, 1, 0, 0));
        relation.add(Arrays.asList(1, 1, 1, 1));
        relation.add(Arrays.asList(0, 1, 0, 1));

        Set<List<Integer>> resultAT = new AT().applyTo(relation);
        System.out.println(resultAT.size());
        for (List<Integer> r : resultAT) {
            System.out.println(r);
        }
    }

}
