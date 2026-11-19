package com.cibertec.evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String especialidad;
}
