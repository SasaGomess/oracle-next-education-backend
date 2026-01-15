package colecoes;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> funcionarios = new ArrayList<>();
        funcionarios.add("Mario");
        funcionarios.add("Julia");
        funcionarios.add("Mario");

        funcionarios.forEach(System.out::println);
        System.out.println(funcionarios.get(1));
        System.out.println(funcionarios.getLast());
        System.out.println("-----------");

        Set<String> produtos = new HashSet<>();

        produtos.add("Suco de Laranja");
        produtos.add("Arroz");
        produtos.add("Macarrão");
        produtos.add("Macarrão");


        produtos.forEach(System.out::println);
        System.out.println("-----------");

        Map<Integer, String> clientes = new HashMap<>();
        clientes.put(1, "Maria");
        clientes.put(2, "João");
        clientes.put(3, "Roberta");
        clientes.put(1, "Julia");
        clientes.put(4, "Ana");

        System.out.println(clientes.get(4));


        Map.Entry<Integer, String> firstMap = clientes.entrySet().stream().findFirst().orElseThrow();

        System.out.println(firstMap);

        for (Map.Entry<Integer, String> entry : clientes.entrySet()){
           if (entry.getKey().equals(1)){
               System.out.println(entry.getKey() + ", " +entry.getValue());
           }
       }
    }
}
