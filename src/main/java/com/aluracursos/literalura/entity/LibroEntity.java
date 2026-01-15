package com.aluracursos.literalura.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "libros", uniqueConstraints = {
        @UniqueConstraint(name = "uk_libros_gutendex_id", columnNames = "gutendexId")
})
public class LibroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer gutendexId;

    @Column(nullable = false, length = 500)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private AutorEntity autor;

    @Column(length = 20)
    private String idioma;

    private Integer numeroDescargas;

    public LibroEntity() {}

    public LibroEntity(Integer gutendexId, String titulo, AutorEntity autor, String idioma, Integer numeroDescargas) {
        this.gutendexId = gutendexId;
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
    }

    public Long getId() { return id; }
    public Integer getGutendexId() { return gutendexId; }
    public String getTitulo() { return titulo; }
    public AutorEntity getAutor() { return autor; }
    public String getIdioma() { return idioma; }
    public Integer getNumeroDescargas() { return numeroDescargas; }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | %s | %d descargas",
                gutendexId,
                titulo,
                autor == null ? "Autor desconocido" : autor.getNombre(),
                idioma == null ? "?" : idioma,
                numeroDescargas == null ? 0 : numeroDescargas
        );
    }
}

