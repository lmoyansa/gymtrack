package com.example.gymtrack.model;

import java.util.Date;

public class Entrenamiento {

    private int idEntrenamiento;
    private int idUsuario;
    private int idRutina;
    private Date fecha;
    private int duracionMinutos;
    private String observaciones;

    public Entrenamiento(int idEntrenamiento, int idUsuario, int idRutina, Date fecha, int duracionMinutos, String observaciones) {
        this.idEntrenamiento = idEntrenamiento;
        this.idUsuario = idUsuario;
        this.idRutina = idRutina;
        this.fecha = fecha;
        this.duracionMinutos = duracionMinutos;
        this.observaciones = observaciones;
    }

    public int getIdEntrenamiento() {
        return idEntrenamiento;
    }

    public void setIdEntrenamiento(int idEntrenamiento) {
        this.idEntrenamiento = idEntrenamiento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}