package com.example.gymtrack.repository;

import com.example.gymtrack.data.GymTrackDatabase;
import com.example.gymtrack.data.dao.EntrenamientoDao;
import com.example.gymtrack.model.Entrenamiento;

import java.util.Date;
import java.util.List;

public class EntrenamientoRepository {

    private static EntrenamientoRepository instance;

    private final EntrenamientoDao
            entrenamientoDao;

    private EntrenamientoRepository() {
        GymTrackDatabase database =
                GymTrackDatabase.obtenerInstancia();

        entrenamientoDao =
                database.entrenamientoDao();
    }

    public static EntrenamientoRepository
    getInstance() {

        if (instance == null) {
            instance =
                    new EntrenamientoRepository();
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
        Entrenamiento entrenamiento =
                new Entrenamiento(
                        0,
                        idUsuario,
                        idRutina,
                        fecha,
                        duracionMinutos,
                        observaciones
                );

        long idGenerado =
                entrenamientoDao.insertar(
                        entrenamiento
                );

        entrenamiento.setIdEntrenamiento(
                (int) idGenerado
        );

        return entrenamiento;
    }

    public Entrenamiento
    obtenerEntrenamientoPorId(
            int idEntrenamiento
    ) {
        return entrenamientoDao.obtenerPorId(
                idEntrenamiento
        );
    }

    public List<Entrenamiento>
    obtenerEntrenamientos() {

        return entrenamientoDao.obtenerTodos();
    }

    public List<Entrenamiento>
    obtenerEntrenamientosPorUsuario(
            int idUsuario
    ) {
        return entrenamientoDao
                .obtenerPorUsuario(
                        idUsuario
                );
    }

    public List<Entrenamiento> obtenerPorRutina(
            int idRutina
    ) {
        return entrenamientoDao.obtenerPorRutina(
                idRutina
        );
    }

    public List<Entrenamiento>
    obtenerPorRutinaYUsuario(
            int idRutina,
            int idUsuario
    ) {
        return entrenamientoDao
                .obtenerPorRutinaYUsuario(
                        idRutina,
                        idUsuario
                );
    }
}