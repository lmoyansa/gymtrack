package com.example.gymtrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.gymtrack.data.DateConverter;

import java.util.Date;

@Entity(tableName = "rutinas")
@TypeConverters(DateConverter.class)
public class Rutina {

    @PrimaryKey(autoGenerate = true)
    private int idRutina;

    private int idUsuario;
    private String nombre;
    private String descripcion;
    private String objetivo;
    private Date fechaCreacion;
    private boolean activa;

    public Rutina(
            int idRutina,
            int idUsuario,
            String nombre,
            String descripcion,
            String objetivo,
            Date fechaCreacion,
            boolean activa
    ) {
        this.idRutina = idRutina;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.objetivo = objetivo;
        this.fechaCreacion = fechaCreacion;
        this.activa = activa;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(
            String descripcion
    ) {
        this.descripcion = descripcion;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(
            Date fechaCreacion
    ) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}