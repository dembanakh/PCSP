package functionsets;

import operators.IOperator;
import task1.BooleanFunction;
import utils.Util;

import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public final class FunctionSetUtil {

    public static FunctionSet of(int arityBound, Predicate<IOperator> predicate) {
        FunctionSet fs = new FunctionSet();

        StreamSupport.stream(BooleanFunction.iterateAll(arityBound).spliterator(), true)
                .filter(predicate)
                .forEach(fs::add);

        return fs;
    }

    private FunctionSetUtil() {}
}
