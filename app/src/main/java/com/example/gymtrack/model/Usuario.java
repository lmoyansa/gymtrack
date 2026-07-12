package com.example.gymtrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.gymtrack.data.DateConverter;

import java.util.Date;

@Entity(tableName = "usuarios")
@TypeConverters(DateConverter.class)
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int idUsuario;

    private String nombre;
    private String email;
    private String passwordHash;
    private Date fechaRegistro;

    public Usuario(
            int idUsuario,
            String nombre,
            String email,
            String passwordHash,
            Date fechaRegistro
    ) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(
            String passwordHash
    ) {
        this.passwordHash = passwordHash;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(
            Date fechaRegistro
    ) {
        this.fechaRegistro = fechaRegistro;
    }
}