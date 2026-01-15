package com.aluracursos.literalura;

import com.aluracursos.literalura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LiteraluraApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Bean
    CommandLineRunner run(Principal principal) {
        return args -> principal.ejecutar();
    }
}

