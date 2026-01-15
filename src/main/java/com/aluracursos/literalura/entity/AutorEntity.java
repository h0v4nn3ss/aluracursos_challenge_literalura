package com.aluracursos.literalura.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "autores", uniqueConstraints = {
        @UniqueConstraint(name = "uk_autores_nombre", columnNames = "nombre")
})
public class AutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String nombre;

    private Integer anoNacimiento;
    private Integer anoFallecimiento;

    public AutorEntity() {}

    public AutorEntity(String nombre, Integer anoNacimiento, Integer anoFallecimiento) {
        this.nombre = nombre;
        this.anoNacimiento = anoNacimiento;
        this.anoFallecimiento = anoFallecimiento;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Integer getAnoNacimiento() { return anoNacimiento; }
    public Integer getAnoFallecimiento() { return anoFallecimiento; }

    @Override
    public String toString() {
        String nac = (anoNacimiento == null) ? "?" : anoNacimiento.toString();
        String fall = (anoFallecimiento == null) ? "?" : anoFallecimiento.toString();
        return String.format("%s (%s - %s)", nombre, nac, fall);
    }
}

