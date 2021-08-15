package task1;

import operators.IOperator;
import utils.TupleGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoldedBooleanFunction extends BooleanFunction {

    public FoldedBooleanFunction(int arity, List<Integer> values) {
        super(arity, mirror(values));
    }

    private static List<Integer> mirror(List<Integer> x) {
        List<Integer> result = new ArrayList<>(x);
        int size = x.size();
        for (int i = size - 1; i >= 0; i--) {
            result.add(1 - x.get(i));
        }
        return result;
    }

    public static Iterable<IOperator> iterateAll(int arity) {
        return () -> new FoldedBooleanFunctionsIterator(arity);
    }

    private static class FoldedBooleanFunctionsIterator implements Iterator<IOperator> {

        private final int arity;
        private final Iterator<List<Integer>> generator;

        FoldedBooleanFunctionsIterator(int arity) {
            this.arity = arity;
            this.generator = new TupleGenerator(1 << (arity - 1)).iterator();
        }

        @Override
        public boolean hasNext() {
            return generator.hasNext();
        }

        @Override
        public IOperator next() {
            return new FoldedBooleanFunction(arity, generator.next());
        }
    }
}
