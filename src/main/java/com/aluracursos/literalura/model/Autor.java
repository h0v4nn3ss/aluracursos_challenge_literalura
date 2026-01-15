package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Autor(
        String name,

        @JsonAlias("birth_year")
        Integer anoNacimiento,

        @JsonAlias("death_year")
        Integer anoFallecimiento
) {}

