package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.EpisodioResponse;
import br.com.alura.screenmatch.dto.SerieResponse;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    private SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @GetMapping()
    public List<SerieResponse> getSeries(){
        return serieService.getSeries();
    }

    @GetMapping("/top5")
    public List<SerieResponse> getTop5Series(){
        return serieService.getTop5();
    }

    @GetMapping("/lancamentos")
    public List<SerieResponse> getLastReleasedSeries(){
        return serieService.getLastReleasedEpisodes();
    }

    @GetMapping("/{id}")
    public SerieResponse getSerieById(@PathVariable Long id){
        return serieService.getSerieById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioResponse> getTodasTemporadas(@PathVariable Long id){
        return serieService.getAllEpisodes(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioResponse> getEpisodiosByTemporada(@PathVariable Long id, @PathVariable Integer numero){
        return serieService.getTemporadaByNumero(id, numero);
    }

    @GetMapping("/categoria/{categoria}")
    public List<SerieResponse> getSeriesByCategoria(@PathVariable String categoria){
        return serieService.getSerieByCategoria(categoria);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioResponse> getTop5Episodes(@PathVariable Long id) {
        return serieService.getTop5Episodes(id);
    }
}
