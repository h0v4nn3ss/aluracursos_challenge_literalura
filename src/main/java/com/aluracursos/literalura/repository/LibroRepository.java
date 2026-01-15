package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.entity.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<LibroEntity, Long> {

    // derived query: evita duplicados por gutendexId
    Optional<LibroEntity> findByGutendexId(Integer gutendexId);

    // derived query: filtra por idioma
    List<LibroEntity> findByIdiomaIgnoreCase(String idioma);

    // derived query (para el futuro si quieres buscar en BD también)
    List<LibroEntity> findByTituloContainingIgnoreCase(String titulo);
}

