package execicios;

import java.awt.geom.Arc2D;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> nomes = new ArrayList<>(List.of("João", "Maria", "Vitor", "Ana"));

        System.out.println("Lista de Funcionários: " + nomes);

        List<String> alunos = new ArrayList<>(List.of("Joana", "Lucas", "Pedro", "Antônio"));

        alunos.remove(2);

        System.out.println(alunos);
        List<String> funcionarios = new ArrayList<>(List.of("João", "Maria", "Ana", "Pedro", "Antônio"));

        System.out.println("A segunda pessoa da lista é: "+funcionarios.get(1));
        System.out.println("Total de funcionarios "+ funcionarios.size());

        Set<String> eventos = new HashSet<>();

        eventos.add("IA Conference Brasil");
        eventos.add("AI Summit");
        eventos.add("DevFest");
        eventos.add("Cloud Expo");
        eventos.add("IA Conference Brasil");
        eventos.add("DevFest");

        System.out.println("Lista de eventos: " + eventos);

        Map<Integer, String> clientes = new HashMap<>();

        clientes.put(2, "Marcos");
        clientes.put(1, "Maria");
        clientes.put(3, "Henrique");
        clientes.put(4, "Joana");
        clientes.put(5, "Karen");

        Set<Map.Entry<Integer, String>> clientesEntry = clientes.entrySet();

        clientesEntry.stream().filter(e -> e.getKey().equals(2)).forEach(e -> System.out.println("O nome do cliente com ID " + e.getKey() + " é: " + e.getValue()));

        for (Map.Entry<Integer, String> entry: clientesEntry ){
            if(clientes.containsKey(entry.getKey()))
                System.out.println(entry.getValue());
        }
        List<String> funcionariosRh = List.of("Ana", "Bruno", "Carlos", "Amanda", "Alice", "Daniel", "Caroline");

        List<String> nomesFiltrados = funcionariosRh.stream().filter(n -> n.length() <= 5).toList();

        System.out.println(nomesFiltrados);

        List<Integer> numeros = List.of(2, 3, 5, 7, 11);
        List<Integer> listaAoQuadrado = numeros.stream().map(n ->(int) Math.pow(n, 2)).toList();

        System.out.println(listaAoQuadrado);

        List<Double> precosProdutos = List.of(29.99, 49.50, 15.75, 99.99);

        Double somaProdutos = precosProdutos.stream().reduce(0.0, Double::sum);

        System.out.printf("Valor total antes do imposto de 8%%: %.2f %n", somaProdutos);
        System.out.printf("Valor total com imposto de 8%%: %.2f %n", ((somaProdutos * 0.08) + somaProdutos));

        List<Double> notas = List.of(7.5, 8.0, 6.5, 9.0, 10.0);

        Double somaTotalNotas = notas.stream().reduce(0.0, Double::sum);
        Double mediaNotas = notas.stream().collect(Collectors.averagingDouble(Double::doubleValue));
        Double menorNota = notas.stream().min(Double::compare).orElseThrow();
        Double maiorNota = notas.stream().max(Double::compare).orElseThrow();

        System.out.println(somaTotalNotas);
        System.out.println(mediaNotas);
        System.out.println(menorNota);
        System.out.println(maiorNota);

    }

}
