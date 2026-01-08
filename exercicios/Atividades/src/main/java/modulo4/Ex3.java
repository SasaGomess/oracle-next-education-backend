package modulo4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ex3 {
    public static void main(String[] args) {
        List<String> nomes = Arrays.asList("Alice", "Bob", "Charlie");

        String collect = nomes.stream().collect(Collectors.joining(", "));

        System.out.println(collect);
    }
}
