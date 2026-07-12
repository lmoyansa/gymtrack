package com.example.gymtrack.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymtrack.model.SerieEntrenamiento;

import java.util.List;

@Dao
public interface SerieEntrenamientoDao {

    @Insert
    long insertar(
            SerieEntrenamiento serie
    );

    @Update
    int actualizar(
            SerieEntrenamiento serie
    );

    @Query(
            "SELECT * FROM series_entrenamiento "
                    + "WHERE idSerie = :idSerie "
                    + "LIMIT 1"
    )
    SerieEntrenamiento obtenerPorId(
            int idSerie
    );

    @Query(
            "SELECT * FROM series_entrenamiento "
                    + "WHERE idEntrenamiento = "
                    + ":idEntrenamiento "
                    + "ORDER BY numeroSerie ASC"
    )
    List<SerieEntrenamiento>
    obtenerPorEntrenamiento(
            int idEntrenamiento
    );

    @Query(
            "SELECT * FROM series_entrenamiento "
                    + "ORDER BY idSerie ASC"
    )
    List<SerieEntrenamiento> obtenerTodas();

    @Query(
            "DELETE FROM series_entrenamiento "
                    + "WHERE idEntrenamiento = "
                    + ":idEntrenamiento"
    )
    int eliminarPorEntrenamiento(
            int idEntrenamiento
    );
}