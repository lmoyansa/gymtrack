package com.example.gymtrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rutina_ejercicios")
public class RutinaEjercicio {

    @PrimaryKey(autoGenerate = true)
    private int idRutinaEjercicio;

    private int idRutina;
    private int idEjercicio;
    private int orden;
    private int seriesPlanificadas;
    private int repeticionesPlanificadas;
    private double pesoObjetivo;
    private int descansoSegundos;

    public RutinaEjercicio(
            int idRutinaEjercicio,
            int idRutina,
            int idEjercicio,
            int orden,
            int seriesPlanificadas,
            int repeticionesPlanificadas,
            double pesoObjetivo,
            int descansoSegundos
    ) {
        this.idRutinaEjercicio =
                idRutinaEjercicio;

        this.idRutina = idRutina;
        this.idEjercicio = idEjercicio;
        this.orden = orden;

        this.seriesPlanificadas =
                seriesPlanificadas;

        this.repeticionesPlanificadas =
                repeticionesPlanificadas;

        this.pesoObjetivo = pesoObjetivo;

        this.descansoSegundos =
                descansoSegundos;
    }

    public int getIdRutinaEjercicio() {
        return idRutinaEjercicio;
    }

    public void setIdRutinaEjercicio(
            int idRutinaEjercicio
    ) {
        this.idRutinaEjercicio =
                idRutinaEjercicio;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(
            int idEjercicio
    ) {
        this.idEjercicio = idEjercicio;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getSeriesPlanificadas() {
        return seriesPlanificadas;
    }

    public void setSeriesPlanificadas(
            int seriesPlanificadas
    ) {
        this.seriesPlanificadas =
                seriesPlanificadas;
    }

    public int getRepeticionesPlanificadas() {
        return repeticionesPlanificadas;
    }

    public void setRepeticionesPlanificadas(
            int repeticionesPlanificadas
    ) {
        this.repeticionesPlanificadas =
                repeticionesPlanificadas;
    }

    public double getPesoObjetivo() {
        return pesoObjetivo;
    }

    public void setPesoObjetivo(
            double pesoObjetivo
    ) {
        this.pesoObjetivo = pesoObjetivo;
    }

    public int getDescansoSegundos() {
        return descansoSegundos;
    }

    public void setDescansoSegundos(
            int descansoSegundos
    ) {
        this.descansoSegundos =
                descansoSegundos;
    }
}