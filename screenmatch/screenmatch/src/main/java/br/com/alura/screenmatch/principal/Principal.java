package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
			json = consumoApi.obterDados(ENDERECO + nomeSerieCodificada+ "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);

			temporadas.add(dadosTemporada);
		}

        temporadas.forEach(System.out::println);

//        List<DadosEpisodio> episodios = temporadas.stream().map(DadosTemporada::episodios)
//                .flatMap(List::stream)
//                .toList();


        temporadas.forEach(t -> t.episodios()
                .forEach(ep -> System.out.println(ep.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 5 episodios:");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5).forEach(System.out::println);

    }
}
