package modulo2.ex5;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(List.of(1,4,5,7,5,2,8,9,23,35,32));

        MultiplyNumbers mn = list -> list.replaceAll(n -> n * 3);

        numbers.forEach(System.out::println);

        mn.multiplyFor3(numbers);
        System.out.println("===================================");

        numbers.forEach(System.out::println);
    }
}
