package com.cibertec.evaluacion.services;

import com.cibertec.evaluacion.dtos.EmpleadoDTO;
import com.cibertec.evaluacion.dtos.EmpleadoSolicitud;

import java.util.List;

public interface IEmpleadoService {
    List<EmpleadoDTO> listar(String filtro);
    EmpleadoDTO buscar(Integer id);
    EmpleadoDTO registrar(EmpleadoSolicitud solicitud);
    EmpleadoDTO actualizar(Integer id, EmpleadoSolicitud solicitud);
    void eliminar(Integer id);
}
