package task1;

import operators.IOperator;
import utils.TupleGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IdempotentFoldedBooleanFunction extends FoldedBooleanFunction {

    public IdempotentFoldedBooleanFunction(int arity, List<Integer> values) {
        super(arity, insertZero(values));
    }

    private static List<Integer> insertZero(List<Integer> x) {
        List<Integer> result = new ArrayList<>(x);
        result.add(0, 0);
        return result;
    }

    public static Iterable<IOperator> iterateAll(int arity) {
        return () -> new IdempotentFoldedBooleanFunctionsIterator(arity);
    }

    private static class IdempotentFoldedBooleanFunctionsIterator implements Iterator<IOperator> {

        private final int arity;
        private final Iterator<List<Integer>> generator;

        IdempotentFoldedBooleanFunctionsIterator(int arity) {
            this.arity = arity;
            this.generator = new TupleGenerator((1 << (arity - 1)) - 1).iterator();
        }

        @Override
        public boolean hasNext() {
            return generator.hasNext();
        }

        @Override
        public IOperator next() {
            return new IdempotentFoldedBooleanFunction(arity, generator.next());
        }
    }

}
