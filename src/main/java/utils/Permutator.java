package utils;

import com.google.common.collect.Lists;

import java.util.*;

public class Permutator implements Iterable<List<List<Integer>>> {

    private final List<List<List<Integer>>> cartProd;

    /*
     * Produces all lists of size k with elements from s
     */
    public Permutator(List<List<Integer>> s, int k) {
        List<List<List<Integer>>> lists = new ArrayList<>();
        for (int i = 0; i < k; i++) lists.add(s);
        cartProd = Lists.cartesianProduct(lists);
    }

    @Override
    public Iterator<List<List<Integer>>> iterator() {
        return cartProd.iterator();
    }

}
