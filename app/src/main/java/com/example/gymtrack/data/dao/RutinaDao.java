package com.example.gymtrack.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymtrack.model.Rutina;

import java.util.List;

@Dao
public interface RutinaDao {

    @Insert
    long insertar(Rutina rutina);

    @Update
    int actualizar(Rutina rutina);

    @Query(
            "SELECT * FROM rutinas "
                    + "WHERE idRutina = :idRutina "
                    + "LIMIT 1"
    )
    Rutina obtenerPorId(int idRutina);

    @Query(
            "SELECT * FROM rutinas "
                    + "WHERE idRutina = :idRutina "
                    + "AND idUsuario = :idUsuario "
                    + "LIMIT 1"
    )
    Rutina obtenerPorIdYUsuario(
            int idRutina,
            int idUsuario
    );

    @Query(
            "SELECT * FROM rutinas "
                    + "WHERE idUsuario = :idUsuario "
                    + "ORDER BY idRutina ASC"
    )
    List<Rutina> obtenerPorUsuario(
            int idUsuario
    );

    @Query(
            "SELECT * FROM rutinas "
                    + "ORDER BY idRutina ASC"
    )
    List<Rutina> obtenerTodas();

    @Query(
            "DELETE FROM rutinas "
                    + "WHERE idRutina = :idRutina"
    )
    int eliminarPorId(int idRutina);
}