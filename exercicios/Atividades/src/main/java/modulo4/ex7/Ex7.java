package modulo4.ex7;

import java.util.*;
import java.util.stream.Collectors;

public class Ex7 {
    public static void main(String[] args) {
        List<Produto> produtos = Arrays.asList(
                new Produto("Smartphone", 800.0, "Eletrônicos"),
                new Produto("Notebook", 1500.0, "Eletrônicos"),
                new Produto("Teclado", 200.0, "Eletrônicos"),
                new Produto("Cadeira", 300.0, "Móveis"),
                new Produto("Monitor", 900.0, "Eletrônicos"),
                new Produto("Mesa", 700.0, "Móveis")
        );

        List<Produto> eletronicos = produtos.stream().filter(p -> p.getCategoria().equalsIgnoreCase("Eletrônicos")).filter(p -> p.getPreco() < 1000).sorted(Comparator.comparing(Produto::getPreco)).collect(Collectors.toList());

        Map<String, Double> somaDosPrecosDaCategoria = produtos.stream().collect(Collectors.groupingBy(Produto::getCategoria, Collectors.summingDouble(Produto::getPreco)));
    }
}
