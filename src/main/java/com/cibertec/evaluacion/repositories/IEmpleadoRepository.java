package com.cibertec.evaluacion.repositories;

import com.cibertec.evaluacion.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEmpleadoRepository extends JpaRepository<EmpleadoEntity, Integer> {
    List<EmpleadoEntity> findAllByOrderByIdAsc();

    @Query("""
            select e from EmpleadoEntity e
            where locate(lower(:filtro), lower(e.nombre)) > 0
               or locate(lower(:filtro), lower(e.apellido)) > 0
               or locate(lower(:filtro), lower(e.especialidad)) > 0
            order by e.id
            """)
    List<EmpleadoEntity> filtrar(@Param("filtro") String filtro);
}
