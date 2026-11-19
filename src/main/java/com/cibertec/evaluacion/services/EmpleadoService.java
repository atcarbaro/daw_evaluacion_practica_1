package com.cibertec.evaluacion.services;

import com.cibertec.evaluacion.dtos.EmpleadoDTO;
import com.cibertec.evaluacion.dtos.EmpleadoSolicitud;
import com.cibertec.evaluacion.entity.EmpleadoEntity;
import com.cibertec.evaluacion.repositories.IEmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmpleadoService implements IEmpleadoService {
    private final IEmpleadoRepository repository;

    @Override
    public List<EmpleadoDTO> listar(String filtro) {
        List<EmpleadoEntity> empleados = filtro == null || filtro.isBlank()
                ? repository.findAllByOrderByIdAsc()
                : repository.filtrar(filtro.strip());
        return empleados.stream().map(this::toDTO).toList();
    }

    @Override
    public EmpleadoDTO buscar(Integer id) {
        return toDTO(buscarEntidad(id));
    }

    @Override
    @Transactional
    public EmpleadoDTO registrar(EmpleadoSolicitud solicitud) {
        EmpleadoEntity empleado = EmpleadoEntity.builder()
                .nombre(solicitud.getNombre())
                .apellido(solicitud.getApellido())
                .email(solicitud.getEmail())
                .especialidad(solicitud.getEspecialidad())
                .build();
        return toDTO(repository.save(empleado));
    }

    @Override
    @Transactional
    public EmpleadoDTO actualizar(Integer id, EmpleadoSolicitud solicitud) {
        EmpleadoEntity empleado = buscarEntidad(id);
        empleado.actualizar(solicitud.getNombre(), solicitud.getApellido(),
                solicitud.getEmail(), solicitud.getEspecialidad());
        return toDTO(empleado);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        repository.delete(buscarEntidad(id));
    }

    private EmpleadoEntity buscarEntidad(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
    }

    private EmpleadoDTO toDTO(EmpleadoEntity empleado) {
        return new EmpleadoDTO(empleado.getId(), empleado.getNombre(), empleado.getApellido(),
                empleado.getEmail(), empleado.getEspecialidad());
    }
}
