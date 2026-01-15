package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

    // (Recomendado) Evitar duplicados por nombre al guardar
    Optional<AutorEntity> findByNombreIgnoreCase(String nombre);

    // (Obligatorio) Lista de autores ordenada
    List<AutorEntity> findAllByOrderByNombreAsc();

    // (Obligatorio) Autores vivos en un ano
    @Query("""
           SELECT a
           FROM AutorEntity a
           WHERE a.anoNacimiento <= :ano
             AND (a.anoFallecimiento IS NULL OR a.anoFallecimiento >= :ano)
           ORDER BY a.nombre
           """)
    List<AutorEntity> autoresVivosEnAno(@Param("ano") Integer ano);
}

