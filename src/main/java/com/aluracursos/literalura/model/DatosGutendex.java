package com.aluracursos.literalura.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosGutendex(
        int count,
        String next,
        String previous,
        List<Libro> results
) {}

