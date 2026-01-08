package br.com.sabrinaweb.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(@JsonAlias("codigo") String codigo, @JsonAlias("nome") String descricao) {
    @Override
    public String toString() {
        return "Cód: " + codigo + " Descrição: " + descricao;
    }
}
