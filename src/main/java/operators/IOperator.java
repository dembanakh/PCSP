package operators;

import java.util.ArrayList;
import java.util.List;

public interface IOperator {
    int getArity();
    int applyOp(List<Integer> x);

    default List<Integer> applyPoly(List<List<Integer>> M) {
        if (M == null || M.size() == 0 || M.size() != getArity())
            throw new RuntimeException("Bad input to applyPoly function of class" + this.getClass().getSimpleName());

        int tuple_arity = M.get(0).size();
        List<Integer> evaluation = new ArrayList<>();
        for (int i = 0; i < tuple_arity; i++) {
            List<Integer> x = new ArrayList<>();
            for (int j = 0; j < getArity(); j++) x.add(M.get(j).get(i));
            evaluation.add(this.applyOp(x));
        }

        return evaluation;
    }

    String toString();
}
