package com.cibertec.evaluacion.controllers;

import com.cibertec.evaluacion.dtos.EmpleadoDTO;
import com.cibertec.evaluacion.dtos.EmpleadoSolicitud;
import com.cibertec.evaluacion.services.IEmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final IEmpleadoService service;

    @GetMapping
    public List<EmpleadoDTO> listar(@RequestParam(required = false) String filtro) {
        return service.listar(filtro);
    }

    @GetMapping("/{id}")
    public EmpleadoDTO buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO> registrar(@Valid @RequestBody EmpleadoSolicitud solicitud) {
        EmpleadoDTO empleado = service.registrar(solicitud);
        return ResponseEntity.created(URI.create("/api/empleados/" + empleado.getId())).body(empleado);
    }

    @PutMapping("/{id}")
    public EmpleadoDTO actualizar(@PathVariable Integer id, @Valid @RequestBody EmpleadoSolicitud solicitud) {
        return service.actualizar(id, solicitud);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
