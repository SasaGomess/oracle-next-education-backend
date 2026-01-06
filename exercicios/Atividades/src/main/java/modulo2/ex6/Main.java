package modulo2.ex6;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>(List.of("Sabrina", "Jessica", "Harvy", "Mike", "Paulo", "Louis"));

        strings.sort(Comparator.comparing(String::valueOf));

        strings.forEach(System.out::println);
    }
}
