package com.aluracursos.literalura;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Sin BD por ahora; este test levanta el contexto completo y falla sin datasource")
@SpringBootTest
class LiteraluraApplicationTests {

        @Test
            void contextLoads() {
                    }

}
