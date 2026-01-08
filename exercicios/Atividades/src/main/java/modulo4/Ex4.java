package modulo4;

import java.util.Arrays;
import java.util.List;

public class Ex4 {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);

        Integer reduced = numeros.stream().filter(e -> e % 2 == 0).map(n -> (int) Math.pow(n, 2)).reduce(0, Integer::sum);
        System.out.println(reduced);
    }
}
