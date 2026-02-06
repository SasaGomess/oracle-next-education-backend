package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + System.getenv("OMDB_API_KEY");
    private List<DadosSerie> listaDadosSeries = new ArrayList<>();

    private SerieRepository repositorio;
    private List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Principal() {

    }

    public void exibeMenu() {
        var opcao = 0;
        do {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título 
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    try {
                        buscarEpisodioPorSerie();
                    }catch (IllegalArgumentException e ){
                        System.out.println("Erro: "+ e.getMessage());
                    }
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    listarSeriePorTitulo();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }while (opcao != 0);
    }



    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        //listaSeries.add(dados);
        Serie serie = new Serie(dados);
        repositorio.save(serie);
        System.out.println(serie);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var nomeSerieCodificada = URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8);
        var json = consumo.obterDados(ENDERECO + nomeSerieCodificada + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        return dados;
    }

    private void buscarEpisodioPorSerie(){
        listarSeriesBuscadas();
        System.out.println("Digite o nome da série para buscar os episódios: ");
        var nomeSerie = leitura.nextLine();

        var serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie)
                .orElseThrow(() -> new IllegalArgumentException("Série não encontrada"));

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= serieBuscada.getTotalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + serieBuscada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(dt -> dt.episodios().stream()
                        .map(de -> new Episodio(dt.numero(), de)))
                .collect(Collectors.toList());

        serieBuscada.setEpisodios(episodios);

        repositorio.save(serieBuscada);
    }

    private void listarSeriesBuscadas() {
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
    private void listarSeriePorTitulo() {
        System.out.println("Digite o nome da série para buscar os episódios: ");
        var nomeSerie = leitura.nextLine();

        Serie serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie)
                .orElseThrow(() -> new IllegalArgumentException("Série não encontrada"));

        System.out.println("Dados da série: " + serieBuscada);
    }
}