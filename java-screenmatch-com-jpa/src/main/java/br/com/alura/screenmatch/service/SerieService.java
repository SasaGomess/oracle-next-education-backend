package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioResponse;
import br.com.alura.screenmatch.dto.SerieResponse;
import br.com.alura.screenmatch.infra.ResourseNotFoundException;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {
    private SerieRepository repository;

    public SerieService(SerieRepository repository) {
        this.repository = repository;
    }

    public List<SerieResponse> getSeries(){
        List<Serie> series = repository.findAll();
        return dataConvert(series);
    }

    public List<SerieResponse> getTop5() {
        List<Serie> top5Series = repository.findTop5ByOrderByAvaliacaoDesc();
        return dataConvert(top5Series);
    }

    public List<SerieResponse> getLastReleasedEpisodes() {
        List<Serie> ultimosLancamentos = repository.ultimosLancamentos();
        return dataConvert(ultimosLancamentos);
    }

    private  List<SerieResponse> dataConvert(List<Serie> objetos){
        return objetos.stream().map(SerieResponse::new).toList();
    }

    public SerieResponse getSerieById(Long id) {
        Serie serie = repository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Serie não encontrada"));
        return new SerieResponse(serie);
    }

    public List<EpisodioResponse> getAllEpisodes(Long id) {
        Serie serie = repository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Serie não encontrada"));

        return serie.getEpisodios().stream().map(e -> new EpisodioResponse(e.getTitulo(), e.getNumeroEpisodio(), e.getTemporada())).toList();
    }

    public List<EpisodioResponse> getTemporadaByNumero(Long id, Integer temporada) {
        Serie serie = repository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Serie não encontrada"));

        return serie.getEpisodios()
                .stream()
                .filter(e -> e.getTemporada().equals(temporada))
                .map(e ->
                        new EpisodioResponse(e.getTitulo(), e.getNumeroEpisodio(), e.getTemporada()))
                .toList();
    }

    public List<SerieResponse> getSerieByCategoria(String categoria) {
        List<Serie> series = repository.seriesPorCategoria(Categoria.fromPortugues(categoria));
        return dataConvert(series);
    }

    public List<EpisodioResponse> getTop5Episodes(Long id) {
        Serie serie = repository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Serie não encontrada"));
        List<Episodio> episodios = repository.topEpisodiosPorSerie(serie);
        return episodios.stream().map(e -> new EpisodioResponse(e.getTitulo(), e.getNumeroEpisodio(), e.getTemporada())).toList();
    }
}
