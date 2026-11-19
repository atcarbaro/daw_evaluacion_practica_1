package com.cibertec.evaluacion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "empleados")
public class EmpleadoEntity extends IdEntity {
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(length = 100)
    private String especialidad;

    @Builder
    private EmpleadoEntity(String nombre, String apellido, String email, String especialidad) {
        actualizar(nombre, apellido, email, especialidad);
    }

    public void actualizar(String nombre, String apellido, String email, String especialidad) {
        this.nombre = nombre.strip();
        this.apellido = apellido.strip();
        this.email = email.strip();
        this.especialidad = especialidad == null || especialidad.isBlank() ? null : especialidad.strip();
    }

}
