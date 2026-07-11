package com.example.gymtrack.model;

public class SerieEntrenamiento {

    private int idSerie;
    private int idEntrenamiento;
    private int idRutinaEjercicio;
    private int numeroSerie;
    private double peso;
    private int repeticiones;
    private boolean completada;

    public SerieEntrenamiento(int idSerie, int idEntrenamiento, int idRutinaEjercicio,
                              int numeroSerie, double peso, int repeticiones, boolean completada) {
        this.idSerie = idSerie;
        this.idEntrenamiento = idEntrenamiento;
        this.idRutinaEjercicio = idRutinaEjercicio;
        this.numeroSerie = numeroSerie;
        this.peso = peso;
        this.repeticiones = repeticiones;
        this.completada = completada;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public int getIdEntrenamiento() {
        return idEntrenamiento;
    }

    public void setIdEntrenamiento(int idEntrenamiento) {
        this.idEntrenamiento = idEntrenamiento;
    }

    public int getIdRutinaEjercicio() {
        return idRutinaEjercicio;
    }

    public void setIdRutinaEjercicio(int idRutinaEjercicio) {
        this.idRutinaEjercicio = idRutinaEjercicio;
    }

    public int getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(int numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}