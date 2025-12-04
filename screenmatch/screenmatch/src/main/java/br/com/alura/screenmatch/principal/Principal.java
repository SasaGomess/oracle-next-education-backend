package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Principal {
    private final Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey="+  System.getenv("OMDB_API_KEY");
    private ConverteDados conversor = new ConverteDados();
    private ConsumoAPI consumoApi = new ConsumoAPI();


    public void exibeMenu(){
        System.out.println("Digite o nome da série para buscar: ");

        var nomeSerie = leitura.nextLine();

        String nomeSerieCodificada = URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8);

        String json = consumoApi.obterDados(ENDERECO + nomeSerieCodificada + API_KEY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dadosSerie);
    }
}
