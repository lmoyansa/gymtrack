package com.example.gymtrack.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymtrack.model.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    long insertar(Usuario usuario);

    @Update
    int actualizar(Usuario usuario);

    @Delete
    int eliminar(Usuario usuario);

    @Query(
            "SELECT * FROM usuarios "
                    + "WHERE idUsuario = :idUsuario "
                    + "LIMIT 1"
    )
    Usuario obtenerPorId(int idUsuario);

    @Query(
            "SELECT * FROM usuarios "
                    + "WHERE LOWER(email) = LOWER(:email) "
                    + "LIMIT 1"
    )
    Usuario obtenerPorEmail(String email);

    @Query(
            "SELECT COUNT(*) FROM usuarios "
                    + "WHERE LOWER(email) = LOWER(:email)"
    )
    int contarPorEmail(String email);

    @Query(
            "SELECT * FROM usuarios "
                    + "ORDER BY idUsuario ASC"
    )
    List<Usuario> obtenerTodos();
}