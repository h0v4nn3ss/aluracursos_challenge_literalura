# LiterAlura

Aplicación **Java + Spring Boot** en modo consola que consume la API pública
**Gutendex** para buscar libros y guarda los resultados en una base de datos
**PostgreSQL** usando **Spring Data JPA**.

El usuario interactúa mediante un menú en la terminal: puede buscar un libro por
título, listar los libros guardados, filtrar por idioma y consultar autores.

## Funcionalidades

- Buscar libro por título (consulta a Gutendex y guarda el primer resultado)
- Listar todos los libros guardados
- Listar libros por idioma
- Listar autores guardados
- Listar autores vivos en un año determinado

## Tecnologías usadas

- Java 25
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- API Gutendex (Project Gutenberg)

## Cómo ejecutar el proyecto

1. Clona el repositorio en tu máquina.
2. Abre el proyecto en tu IDE o editor de preferencia.
3. Ejecuta la clase `LiteraluraApplication`.

La aplicación se ejecuta en modo consola y mostrará un menú interactivo.

## Configuración de PostgreSQL

La aplicación utiliza PostgreSQL para persistir libros y autores.

Configura tus credenciales en:

`src/main/resources/application.properties`

Ejemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
```

## Menú de la aplicación

Al ejecutar la app, se muestra un menú en consola con opciones como:

- Buscar libro por título (consulta la API y guarda el primer resultado)
- Listar todos los libros guardados
- Listar libros por idioma
- Listar autores guardados
- Listar autores vivos en un año determinado

## Notas del desafío

Para simplificar las consultas:

- Se considera que cada libro tiene **solo un idioma** (se guarda el primer
  idioma recibido por la API).
- Se considera que cada libro tiene **solo un autor** (se guarda el primer
  autor recibido por la API).
- Al guardar un libro, también se guarda su autor y se mantiene la relación
  entre ambos.


## Ejemplos de uso

### Ejemplo 1: Buscar libro por título
1. Selecciona la opción **Buscar libro por título**
2. Ingresa un término, por ejemplo: `dickens`
3. La app consultará Gutendex y guardará el **primer resultado encontrado** en
   la base de datos.

---

### Ejemplo 2: Listar libros guardados
1. Selecciona la opción **Listar todos los libros guardados**
2. La app mostrará todos los libros que ya están persistidos en la base de
   datos.

---

### Ejemplo 3: Listar libros por idioma
1. Selecciona la opción **Listar libros por idioma**
2. Ingresa un idioma (ej: `en`, `es`, `fr`)
3. La app mostrará los libros guardados que coincidan con ese idioma.

---

### Ejemplo 4: Listar autores vivos en un año
1. Selecciona la opción **Listar autores vivos en un año**
2. Ingresa un año, por ejemplo: `1900`
3. La app mostrará los autores que estaban vivos en ese año.

