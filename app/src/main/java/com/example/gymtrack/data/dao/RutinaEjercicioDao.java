package com.example.gymtrack.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymtrack.model.RutinaEjercicio;

import java.util.List;

@Dao
public interface RutinaEjercicioDao {

    @Insert
    long insertar(
            RutinaEjercicio rutinaEjercicio
    );

    @Update
    int actualizar(
            RutinaEjercicio rutinaEjercicio
    );

    @Query(
            "SELECT * FROM rutina_ejercicios "
                    + "WHERE idRutinaEjercicio = "
                    + ":idRutinaEjercicio "
                    + "LIMIT 1"
    )
    RutinaEjercicio obtenerPorId(
            int idRutinaEjercicio
    );

    @Query(
            "SELECT * FROM rutina_ejercicios "
                    + "WHERE idRutina = :idRutina "
                    + "ORDER BY orden ASC"
    )
    List<RutinaEjercicio> obtenerPorRutina(
            int idRutina
    );

    @Query(
            "SELECT * FROM rutina_ejercicios "
                    + "ORDER BY idRutinaEjercicio ASC"
    )
    List<RutinaEjercicio> obtenerTodos();

    @Query(
            "DELETE FROM rutina_ejercicios "
                    + "WHERE idRutinaEjercicio = "
                    + ":idRutinaEjercicio"
    )
    int eliminarPorId(
            int idRutinaEjercicio
    );

    @Query(
            "DELETE FROM rutina_ejercicios "
                    + "WHERE idRutina = :idRutina"
    )
    int eliminarPorRutina(int idRutina);
}