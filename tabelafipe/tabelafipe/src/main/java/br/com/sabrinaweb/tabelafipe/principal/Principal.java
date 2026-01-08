package br.com.sabrinaweb.tabelafipe.principal;

import br.com.sabrinaweb.tabelafipe.model.Dados;
import br.com.sabrinaweb.tabelafipe.model.Modelos;
import br.com.sabrinaweb.tabelafipe.model.Veiculo;
import br.com.sabrinaweb.tabelafipe.service.ConsumoAPI;
import br.com.sabrinaweb.tabelafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Principal {
    private final Scanner sc = new Scanner(System.in);
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados converteDados = new ConverteDados();

    public void exibeMenu(){
        var menu = """
                *=--BEM VINDO AO SISTEMA DE CONSULTA A TABELA FIPE--=*
                
                OPÇÕES:
                
                | Carro |
                | Moto |
                | Caminhão |
                
                Digite uma das opções para consultar os valores:""";
        System.out.println(menu);

        String tipoVeiculo = sc.nextLine();

        String veiculoValidado = validaResposta(tipoVeiculo);

        var buscaMarcas = ENDERECO + veiculoValidado + "/marcas/";

        var json = consumoAPI.obterDados(buscaMarcas);

        List<Dados> marcas = converteDados.obterLista(json, Dados.class);

        System.out.println("\n Marcas encontradas: \n");

        marcas.stream()
                .sorted(Comparator.comparing(m -> Integer.valueOf(m.codigo())))
                .forEach(System.out::println);

        System.out.println("\n Digite o código da marca para a consulta:");

        var codigoDigitado = sc.nextLine();

        Dados marcaEncontrada = marcas.stream().filter(m -> m.codigo().equalsIgnoreCase(codigoDigitado)).findFirst().orElseThrow(() -> new IllegalArgumentException("O código passado é inválido"));

        var buscaModelos = buscaMarcas + marcaEncontrada.codigo() + "/modelos/";
        json = consumoAPI.obterDados(buscaModelos);

        Modelos modelos = converteDados.obterDados(json, Modelos.class);

        System.out.println("\n Modelos encontrados: \n");

        modelos.dados().forEach(System.out::println);

        System.out.println("\n Digite um trecho do nome do veículo para consultar: ");

        var trechoNomeVeiculo = sc.nextLine();

        List<Dados> veiculosEncontrados = modelos.dados().stream().filter(m -> m.descricao().toLowerCase().contains(trechoNomeVeiculo.toLowerCase())).toList();

        System.out.println("\n Modelos do "+ veiculosEncontrados.get(0).descricao() + "encontrados: \n");
        veiculosEncontrados.forEach(System.out::println);

        System.out.println("\n Digite o código do modelo para consultar os valores ");

        var codigoModelo = sc.nextLine();

        Dados veiculoEncontrado = veiculosEncontrados.stream().filter(v -> v.codigo().equalsIgnoreCase(codigoModelo)).findFirst().orElseThrow(() -> new IllegalArgumentException("O código passado é inválido"));

        var buscaAnos = buscaModelos + veiculoEncontrado.codigo() + "/anos/";

        json = consumoAPI.obterDados(buscaAnos);

        List<Dados> anosModelo = converteDados.obterLista(json, Dados.class);

        List<Veiculo> listaVeiculos = new ArrayList<>();

        for (int i = 0; i <= anosModelo.size() - 1; i++){
            var buscaVeiculo = buscaAnos + anosModelo.get(i).codigo();
            json = consumoAPI.obterDados(buscaVeiculo);
            Veiculo veiculo = converteDados.obterDados(json, Veiculo.class);
            listaVeiculos.add(veiculo);
        }
        System.out.println("\n Todos os Veiculos avaliados por ano \n");
        listaVeiculos.forEach(System.out::println);
    }

    private String validaResposta(String tipoVeiculo) {
        if (tipoVeiculo.toLowerCase().contains("carr")){
            return "carros";
        } else if (tipoVeiculo.toLowerCase().contains("mot")) {
            return "motos";
        } else if (tipoVeiculo.toLowerCase().contains("cami")){
            return "caminhoes";
        }else {
            throw new IllegalArgumentException("A opção está inválida verifique a lista e tente novamente :)");
        }
    }
}
