
package com.aluracursos.literalura.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsumoAPITest {

    @Test
    void debeObtenerDatosDeLaApi() {
        ConsumoAPI api = new ConsumoAPI();

        String json = api.obtenerDatos("https://gutendex.com/books/");

        assertNotNull(json);
        assertFalse(json.isBlank());
        assertTrue(json.contains("results"));
    }
}
