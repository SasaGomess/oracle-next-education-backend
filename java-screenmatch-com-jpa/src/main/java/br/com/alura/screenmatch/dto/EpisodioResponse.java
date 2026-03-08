package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.model.Serie;

public record EpisodioResponse(String titulo,
                               Integer numeroEpisodio,
                               Integer temporada
                               ) {
}
