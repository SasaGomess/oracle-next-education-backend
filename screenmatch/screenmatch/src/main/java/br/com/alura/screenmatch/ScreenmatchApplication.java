package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	//Similar ao metodo main
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Primeiro projeto Spring sem web");
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("http://www.omdbapi.com/?t=gilmore+girls&apikey=b831fd54");
		System.out.println(json);
		ConverteDados converteDados = new ConverteDados();

		DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);

		json = consumoAPI.obterDados("http://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=b831fd54");

		System.out.println(json);
		DadosEpisodio dadosEpisodio = converteDados.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
			json = consumoAPI.obterDados(String.format("http://www.omdbapi.com/?t=gilmore+girls&season={%d}&apikey=b831fd54", i));
			DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);

			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);


	}
}
