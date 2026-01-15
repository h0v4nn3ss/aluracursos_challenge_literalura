package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.entity.AutorEntity;
import com.aluracursos.literalura.entity.LibroEntity;
import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.DatosGutendex;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos convierteDatos;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    private final Scanner scanner = new Scanner(System.in);

    public Principal(
            ConsumoAPI consumoAPI,
            ConvierteDatos convierteDatos,
            LibroRepository libroRepository,
            AutorRepository autorRepository
    ) {
        this.consumoAPI = consumoAPI;
        this.convierteDatos = convierteDatos;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void ejecutar() {
        int opcion = -1;

        while (opcion != 0) {
            mostrarMenu();
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();     // API + guardar 1er resultado
                case 2 -> listarTodosLosLibros();      // BD
                case 3 -> listarLibrosPorIdioma();     // BD + derived query
                case 4 -> listarAutores();             // BD
                case 5 -> listarAutoresVivosEnAno();   // BD + @Query
                case 0 -> System.out.println("Chao. Vuelve cuando extrañes a Hibernate.");
                default -> System.out.println("Opción inválida.");
            }

            System.out.println();
        }
    }

    private void mostrarMenu() {
        System.out.println("""
                =========================
                LiterAlura (modo consola)
                =========================
                1) Buscar libro por título (guardar 1er resultado)
                2) Listar todos los libros buscados
                3) Listar libros por idioma
                4) Listar autores
                5) Listar autores vivos en un ano
                0) Salir
                """);
        System.out.print("Elige una opción: ");
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.print("Eso no es un número. Intenta de nuevo: ");
            scanner.nextLine();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // consume Enter
        return valor;
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Buscar (título/autor): ");
        String termino = scanner.nextLine().trim();

        if (termino.isBlank()) {
            System.out.println("Búsqueda vacía.");
            return;
        }

        String encoded = URLEncoder.encode(termino, StandardCharsets.UTF_8);
        String url = "https://gutendex.com/books/?search=" + encoded;

        String json = consumoAPI.obtenerDatos(url);
        DatosGutendex datos = convierteDatos.obtenerDatos(json, DatosGutendex.class);

        if (datos.results() == null || datos.results().isEmpty()) {
            System.out.println("No se encontraron libros para: " + termino);
            return;
        }

        // Regla del desafío: SOLO el primer libro
        Libro libro = datos.results().getFirst();

        // Regla del desafío: SOLO el primer autor
        Autor autorApi = (libro.authors() != null && !libro.authors().isEmpty())
                ? libro.authors().getFirst()
                : null;

        String nombreAutor = (autorApi != null && autorApi.name() != null && !autorApi.name().isBlank())
                ? autorApi.name().trim()
                : null;

        Integer anoNacimiento = (autorApi != null) ? autorApi.anoNacimiento() : null;
        Integer anoFallecimiento = (autorApi != null) ? autorApi.anoFallecimiento() : null;

        // Regla del desafío: SOLO el primer idioma
        String idioma = (libro.idiomas() != null && !libro.idiomas().isEmpty())
                ? libro.idiomas().getFirst()
                : null;

        Integer descargas = libro.numeroDescargas();

        // Mostrar en consola (para que se vea sí o sí)
        System.out.println("\nPrimer resultado encontrado:");
        System.out.printf("- [%d] %s | %s | %s | %d descargas%n",
                libro.id(),
                libro.title(),
                (nombreAutor == null ? "Autor desconocido" : nombreAutor),
                (idioma == null ? "?" : idioma),
                (descargas == null ? 0 : descargas)
        );

        // Evitar duplicados por gutendexId
        boolean yaExiste = libroRepository.findByGutendexId(libro.id()).isPresent();
        if (yaExiste) {
            System.out.println("Este libro ya estaba guardado.");
            return;
        }

        // Obtener o crear AutorEntity (evitando duplicado por nombre)
        AutorEntity autorEntity = null;
        if (nombreAutor != null) {
            Optional<AutorEntity> existente = autorRepository.findByNombreIgnoreCase(nombreAutor);
            autorEntity = existente.orElseGet(() ->
                    autorRepository.save(new AutorEntity(nombreAutor, anoNacimiento, anoFallecimiento))
            );
        }

        // Guardar el libro
        LibroEntity entity = new LibroEntity(
                libro.id(),
                libro.title(),
                autorEntity,
                idioma,
                descargas
        );

        libroRepository.save(entity);
        System.out.println("Guardado correctamente.");
    }

    private void listarTodosLosLibros() {
        var libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("Aún no hay libros guardados. Busca algo primero.");
            return;
        }

        System.out.println("\nLibros guardados:");
        libros.forEach(l -> System.out.println("- " + l));
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Idioma (ej: en, es, fr): ");
        String idioma = scanner.nextLine().trim();

        if (idioma.isBlank()) {
            System.out.println("Idioma vacío.");
            return;
        }

        var libros = libroRepository.findByIdiomaIgnoreCase(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados en idioma: " + idioma);
            return;
        }

        System.out.println("\nLibros en idioma '" + idioma + "':");
        libros.forEach(l -> System.out.println("- " + l));
    }

    private void listarAutores() {
        List<AutorEntity> autores = autorRepository.findAllByOrderByNombreAsc();

        if (autores.isEmpty()) {
            System.out.println("Aún no hay autores guardados. Busca un libro primero.");
            return;
        }

        System.out.println("\nAutores guardados:");
        autores.forEach(a -> System.out.println("- " + a));
    }

    private void listarAutoresVivosEnAno() {
        System.out.print("Ano (ej: 1900): ");
        int ano = leerEntero();

        // Ya no usamos derived query: esto viene de @Query
        List<AutorEntity> autores = autorRepository.autoresVivosEnAno(ano);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el ano: " + ano);
            return;
        }

        System.out.println("\nAutores vivos en el ano " + ano + ":");
        autores.forEach(a -> System.out.println("- " + a));
    }
}

