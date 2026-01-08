package modulo4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ex2 {
    public static void main(String[] args) {
        List<String> palavras = Arrays.asList("java", "stream", "lambda", "code");


        Map<Integer, List<String>> collect = palavras.stream().collect(Collectors.groupingBy(String::length, Collectors.toList()));

        System.out.println(collect);

        // Resultado Esperado: {4=[java, code], 6=[stream, lambda]}
    }
}
