package com.example.gymtrack.repository;

import com.example.gymtrack.model.Entrenamiento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntrenamientoRepository {

    private static EntrenamientoRepository instance;

    private final List<Entrenamiento> entrenamientos;
    private int siguienteId;

    private EntrenamientoRepository() {
        entrenamientos = new ArrayList<>();
        siguienteId = 1;
    }

    public static EntrenamientoRepository getInstance() {
        if (instance == null) {
            instance = new EntrenamientoRepository();
        }

        return instance;
    }

    public Entrenamiento crearEntrenamiento(
            int idUsuario,
            int idRutina,
            Date fecha,
            int duracionMinutos,
            String observaciones
    ) {
        Entrenamiento entrenamiento = new Entrenamiento(
                siguienteId,
                idUsuario,
                idRutina,
                fecha,
                duracionMinutos,
                observaciones
        );

        entrenamientos.add(entrenamiento);
        siguienteId++;

        return entrenamiento;
    }

    public Entrenamiento obtenerEntrenamientoPorId(
            int idEntrenamiento
    ) {
        for (Entrenamiento entrenamiento : entrenamientos) {
            if (entrenamiento.getIdEntrenamiento()
                    == idEntrenamiento) {

                return entrenamiento;
            }
        }

        return null;
    }

    public List<Entrenamiento> obtenerEntrenamientos() {
        return new ArrayList<>(entrenamientos);
    }

    public List<Entrenamiento> obtenerPorRutina(
            int idRutina
    ) {
        List<Entrenamiento> resultado = new ArrayList<>();

        for (Entrenamiento entrenamiento : entrenamientos) {
            if (entrenamiento.getIdRutina() == idRutina) {
                resultado.add(entrenamiento);
            }
        }

        return resultado;
    }
}