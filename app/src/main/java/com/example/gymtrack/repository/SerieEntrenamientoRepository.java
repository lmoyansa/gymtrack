package com.example.gymtrack.repository;

import com.example.gymtrack.data.GymTrackDatabase;
import com.example.gymtrack.data.dao.SerieEntrenamientoDao;
import com.example.gymtrack.model.SerieEntrenamiento;

import java.util.List;

public class SerieEntrenamientoRepository {

    private static SerieEntrenamientoRepository
            instance;

    private final SerieEntrenamientoDao
            serieEntrenamientoDao;

    private SerieEntrenamientoRepository() {
        GymTrackDatabase database =
                GymTrackDatabase.obtenerInstancia();

        serieEntrenamientoDao =
                database.serieEntrenamientoDao();
    }

    public static SerieEntrenamientoRepository
    getInstance() {

        if (instance == null) {
            instance =
                    new SerieEntrenamientoRepository();
        }

        return instance;
    }

    public SerieEntrenamiento crearSerie(
            int idEntrenamiento,
            int idRutinaEjercicio,
            int numeroSerie,
            double peso,
            int repeticiones,
            boolean completada
    ) {
        SerieEntrenamiento serie =
                new SerieEntrenamiento(
                        0,
                        idEntrenamiento,
                        idRutinaEjercicio,
                        numeroSerie,
                        peso,
                        repeticiones,
                        completada
                );

        long idGenerado =
                serieEntrenamientoDao.insertar(
                        serie
                );

        serie.setIdSerie(
                (int) idGenerado
        );

        return serie;
    }

    public List<SerieEntrenamiento>
    obtenerPorEntrenamiento(
            int idEntrenamiento
    ) {
        return serieEntrenamientoDao
                .obtenerPorEntrenamiento(
                        idEntrenamiento
                );
    }

    public List<SerieEntrenamiento>
    obtenerSeries() {

        return serieEntrenamientoDao
                .obtenerTodas();
    }
}