package modulo4;

import java.util.Arrays;
import java.util.List;

public class Ex1 {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(10, 20, 30, 40, 50);

        Integer integer = numeros.stream().max(Integer::compareTo).orElseThrow();
        System.out.println(integer);
    }
}
