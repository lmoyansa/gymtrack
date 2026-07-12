package com.example.gymtrack.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymtrack.model.Entrenamiento;

import java.util.List;

@Dao
public interface EntrenamientoDao {

    @Insert
    long insertar(
            Entrenamiento entrenamiento
    );

    @Update
    int actualizar(
            Entrenamiento entrenamiento
    );

    @Query(
            "SELECT * FROM entrenamientos "
                    + "WHERE idEntrenamiento = "
                    + ":idEntrenamiento "
                    + "LIMIT 1"
    )
    Entrenamiento obtenerPorId(
            int idEntrenamiento
    );

    @Query(
            "SELECT * FROM entrenamientos "
                    + "ORDER BY idEntrenamiento ASC"
    )
    List<Entrenamiento> obtenerTodos();

    @Query(
            "SELECT * FROM entrenamientos "
                    + "WHERE idRutina = :idRutina "
                    + "ORDER BY idEntrenamiento ASC"
    )
    List<Entrenamiento> obtenerPorRutina(
            int idRutina
    );

    @Query(
            "DELETE FROM entrenamientos "
                    + "WHERE idEntrenamiento = "
                    + ":idEntrenamiento"
    )
    int eliminarPorId(
            int idEntrenamiento
    );
}