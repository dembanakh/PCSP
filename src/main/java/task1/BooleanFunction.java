package task1;

import operators.IOperator;
import utils.TupleGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BooleanFunction implements IOperator {

    private final int arity;
    private final List<Integer> values;

    public BooleanFunction(int arity, List<Integer> values) {
        this.arity = arity;
        if (values.size() != (1 << arity)) {
            System.err.println("BooleanFunction(): wrong values list size");
        }
        this.values = new ArrayList<>(values);
    }

    @Override
    public int getArity() {
        return this.arity;
    }

    @Override
    public int applyOp(List<Integer> x) {
        return values.get(Util.binaryToInt(x));
    }

    @Override
    public String toString() {
        return values.toString();
    }

    public static Iterable<IOperator> iterateAll(int arity) {
        return () -> new BooleanFunctionsIterator(arity);
    }

    private static class BooleanFunctionsIterator implements Iterator<IOperator> {

        private final int arity;
        private final Iterator<List<Integer>> generator;

        BooleanFunctionsIterator(int arity) {
            this.arity = arity;
            this.generator = new TupleGenerator(1 << arity).iterator();
        }

        @Override
        public boolean hasNext() {
            return generator.hasNext();
        }

        @Override
        public IOperator next() {
            return new BooleanFunction(arity, generator.next());
        }
    }
}
