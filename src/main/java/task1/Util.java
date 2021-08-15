package task1;

import java.util.List;

public class Util {

    public static int binaryToInt(List<Integer> x) {
        int result = 0;
        for (Integer i : x) {
            result <<= 1;
            result += i;
        }
        return result;
    }

}
