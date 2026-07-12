package com.example.gymtrack.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymtrack.model.Ejercicio;

import java.util.List;

@Dao
public interface EjercicioDao {

    @Insert
    long insertar(Ejercicio ejercicio);

    @Update
    int actualizar(Ejercicio ejercicio);

    @Query(
            "SELECT * FROM ejercicios "
                    + "WHERE idEjercicio = :idEjercicio "
                    + "LIMIT 1"
    )
    Ejercicio obtenerPorId(int idEjercicio);

    @Query(
            "SELECT * FROM ejercicios "
                    + "ORDER BY idEjercicio ASC"
    )
    List<Ejercicio> obtenerTodos();

    @Query(
            "DELETE FROM ejercicios "
                    + "WHERE idEjercicio = :idEjercicio"
    )
    int eliminarPorId(int idEjercicio);
}