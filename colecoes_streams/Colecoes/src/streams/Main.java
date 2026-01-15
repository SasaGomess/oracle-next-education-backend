package streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> funcionarios = new ArrayList<>(List.of("Marcos", "Ana", "Carlos", "Amanda"));

        List<String> funcionariosComA = funcionarios.stream().filter(f -> f.startsWith("A") || f.startsWith("a")).toList();

        System.out.println(funcionariosComA);

        List<Double> valoresVendas = List.of(500.00, 1800.00, 6200.00);

        List<Double> comissao = valoresVendas.stream().map(v -> v * 0.05).toList();

        System.out.println(comissao);
        System.out.println(valoresVendas);

        // para evitar o Optional adicionar um identificador
        double totalVendas = valoresVendas.stream().reduce(0.0, Double::sum);

        Map<Double, Double> comissoesCadaVenda = valoresVendas.stream().collect(Collectors.groupingBy(Double::doubleValue, Collectors.summingDouble(v -> v * 0.05)));

        System.out.println(comissoesCadaVenda);
        System.out.println("Total vendas: " + totalVendas);
    }
}
