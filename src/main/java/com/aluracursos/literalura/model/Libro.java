package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Libro(
        int id,
        String title,
        List<Autor> authors,

        @JsonProperty("languages")
        List<String> idiomas,

        @JsonProperty("download_count")
        int numeroDescargas
) {}

