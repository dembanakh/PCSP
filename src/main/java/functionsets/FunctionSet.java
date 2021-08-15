package functionsets;

import operators.IOperator;
import utils.Util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FunctionSet {

    private final Set<IOperator> functions = ConcurrentHashMap.newKeySet();

    public void add(IOperator function) {
        functions.add(function);
    }

    public Set<List<Integer>> applyTo(Collection<List<Integer>> relation) {
        Set<List<Integer>> generations = ConcurrentHashMap.newKeySet();
        functions.parallelStream()
                .map(function ->
                        Util.randomizedSimpleOpGenerate(function, relation, 1000, false))
                .forEach(generations::addAll);
        generations.addAll(relation);
        return generations;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(functions.size()).append('\n');
        sb.append(functions);
        return sb.toString();
    }
}
