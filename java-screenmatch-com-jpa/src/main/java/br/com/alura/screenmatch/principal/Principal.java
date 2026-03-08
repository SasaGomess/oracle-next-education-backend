package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
//@Component
//public class Principal {
//
//    private Scanner leitura = new Scanner(System.in);
//    private ConsumoApi consumo = new ConsumoApi();
//    private ConverteDados conversor = new ConverteDados();
//    private final String ENDERECO = "https://www.omdbapi.com/?t=";
//    private final String API_KEY = "&apikey=" + System.getenv("OMDB_API_KEY");
//    private List<DadosSerie> listaDadosSeries = new ArrayList<>();
//
//    private Serie serieBusca;
//
//    private SerieRepository repositorio;
//    private List<Serie> series = new ArrayList<>();
//
//    public Principal(SerieRepository repositorio) {
//        this.repositorio = repositorio;
//    }
//
//    public Principal() {
//
//    }
//
//    public void exibeMenu() {
//        var opcao = 0;
//        do {
//            var menu = """
//                    1 - Buscar séries
//                    2 - Buscar episódios
//                    3 - Listar séries buscadas
//                    4 - Buscar série por título
//                    5 - Buscar séries por Ator
//                    6 - Top 5 Séries
//                    7 - Buscar Séries por Categoria
//                    8 - Buscar Séries pela quantidade de temporadas
//                    9 - Buscar Episódios pelo trecho do titulo
//                    10 - Buscar Os Top 5 Episdios da Série
//                    11 - Buscar Episódios a partir de uma data
//                    0 - Sair
//                    """;
//
//            System.out.println(menu);
//            opcao = leitura.nextInt();
//            leitura.nextLine();
//
//            switch (opcao) {
//                case 1:
//                    buscarSerieWeb();
//                    break;
//                case 2:
//                    try {
//                        buscarEpisodioPorSerie();
//                    }catch (IllegalArgumentException e ){
//                        System.out.println("Erro: "+ e.getMessage());
//                    }
//                    break;
//                case 3:
//                    listarSeriesBuscadas();
//                    break;
//                case 4:
//                    buscarSeriePorTitulo();
//                    break;
//                case 5:
//                    buscarSeriesPorAtor();
//                    break;
//                case 6:
//                    buscarTop5Series();
//                    break;
//                case 7:
//                    buscarSeriesPorCategoria();
//                    break;
//                case 8:
//                    buscarSeriesPorTemporadaEAvaliacao();
//                    break;
//                case 9:
//                    buscarEpisodioPorTrecho();
//                    break;
//                case 10:
//                    topEpisodiosPorSerie();
//                    break;
//                case 11:
//                    buscaEpisodiosDepoisUmaData();
//                    break;
//                case 0:
//                    System.out.println("Saindo...");
//                    break;
//                default:
//                    System.out.println("Opção inválida");
//            }
//        }while (opcao != 0);
//    }
//
//    private void buscarSerieWeb() {
//        DadosSerie dados = getDadosSerie();
//        //listaSeries.add(dados);
//        Serie serie = new Serie(dados);
//        repositorio.save(serie);
//        System.out.println(serie);
//    }
//
//    private DadosSerie getDadosSerie() {
//        System.out.println("Digite o nome da série para busca");
//        var nomeSerie = leitura.nextLine();
//        var nomeSerieCodificada = URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8);
//        var json = consumo.obterDados(ENDERECO + nomeSerieCodificada + API_KEY);
//        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
//
//        return dados;
//    }
//
//    private void buscarEpisodioPorSerie(){
//        listarSeriesBuscadas();
//        System.out.println("Digite o nome da série para buscar os episódios: ");
//        var nomeSerie = leitura.nextLine();
//
//        var serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie)
//                .orElseThrow(() -> new IllegalArgumentException("Série não encontrada"));
//
//        List<DadosTemporada> temporadas = new ArrayList<>();
//
//        for (int i = 1; i <= serieBuscada.getTotalTemporadas(); i++) {
//            var json = consumo.obterDados(ENDERECO + serieBuscada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
//            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
//            temporadas.add(dadosTemporada);
//        }
//        temporadas.forEach(System.out::println);
//
//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(dt -> dt.episodios().stream()
//                        .map(de -> new Episodio(dt.numero(), de)))
//                .collect(Collectors.toList());
//
//        serieBuscada.setEpisodios(episodios);
//
//        repositorio.save(serieBuscada);
//    }
//
//    private void listarSeriesBuscadas() {
//        series = repositorio.findAll();
//        series.stream()
//                .sorted(Comparator.comparing(Serie::getGenero))
//                .forEach(System.out::println);
//    }
//    private void buscarSeriePorTitulo() {
//        System.out.println("Digite o nome da série para buscar os episódios: ");
//        var nomeSerie = leitura.nextLine();
//
//        try {
//            serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie)
//                    .orElseThrow(() -> new IllegalArgumentException("Série não encontrada"));
//        }catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("Dados da série: " + serieBusca);
//    }
//    private void buscarSeriesPorAtor() {
//        System.out.println("Digite o nome do ator para buscar a série: ");
//        var nomeAtor = leitura.nextLine();
//
//        System.out.println("Avaliações a partir de que valor?");
//        var avaliacao = leitura.nextDouble();
//
//        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
//
//        System.out.println("Série em que " + nomeAtor + " trabalhou");
//        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " " + s.getAvaliacao()));
//
//    }
//    private void buscarTop5Series() {
//        List<Serie> serieTop = repositorio.findTop5ByOrderByAvaliacaoDesc();
//        serieTop.forEach(s -> System.out.println(s.getTitulo() + " " + s.getAvaliacao()));
//    }
//    private void buscarSeriesPorCategoria() {
//        System.out.println("Deseja buscar séries por qual categoria/gênero? ");
//        var nomeCategoria = leitura.nextLine();
//        Categoria categoria = Categoria.fromPortugues(nomeCategoria);
//        List<Serie> seriePorCategoria = repositorio.findByGenero(categoria);
//
//        System.out.println("Série da " + nomeCategoria);
//        seriePorCategoria.forEach(System.out::println);
//    }
//
//    private void buscarSeriesPorTemporadaEAvaliacao() {
//        System.out.println("Digite quantas temporadas você em tempo de maratonar");
//        var quantidadeTemporadas = leitura.nextInt();
//        System.out.println("Avaliações a partir de qual valor? ");
//        var avaliacao = leitura.nextDouble();
//
//        List<Serie> seriesEncontradas = repositorio.seriesPorTemporadaEAvaliacao(quantidadeTemporadas, avaliacao);
//
//        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " " + s.getTotalTemporadas() + " " + s.getAvaliacao()));
//    }
//
//    private void buscarEpisodioPorTrecho() {
//        System.out.println("Qual o nome do episódio para buscar?");
//        var trechoEpisodio = leitura.nextLine();
//        List<Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(trechoEpisodio);
//
//        episodiosEncontrados.forEach(e ->
//                System.out.printf("Série: %s - Temporada: %d - Episódio: %d - %s %n",
//                        e.getSerie().getTitulo(),
//                        e.getTemporada(),
//                        e.getNumeroEpisodio(),
//                        e.getTitulo()));
//    }
//
//    private void topEpisodiosPorSerie() {
//        buscarSeriePorTitulo();
//        List<Episodio> topEpisodios = repositorio.topEpisodiosPorSerie(serieBusca);
//        topEpisodios.forEach(e ->
//                System.out.printf("Série: %s - Avaliação: %.2f - Temporada: %d - Episódio: %d - %s %n",
//                        e.getSerie().getTitulo(),
//                        e.getAvaliacao(),
//                        e.getTemporada(),
//                        e.getNumeroEpisodio(),
//                        e.getTitulo()));
//    }
//
//    private void buscaEpisodiosDepoisUmaData() {
//        buscarSeriePorTitulo();
//        System.out.println("Digite o ano limite de lançamento");
//        var anoLancamento = leitura.nextInt();
//        List<Episodio> episodiosPelaData = repositorio.episodioPorSerieEAno(serieBusca, anoLancamento);
//        episodiosPelaData.forEach(System.out::println);
//    }
//}