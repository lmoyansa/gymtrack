package com.example.gymtrack.repository;

import com.example.gymtrack.model.SerieEntrenamiento;

import java.util.ArrayList;
import java.util.List;

public class SerieEntrenamientoRepository {

    private static SerieEntrenamientoRepository instance;

    private final List<SerieEntrenamiento> series;
    private int siguienteId;

    private SerieEntrenamientoRepository() {
        series = new ArrayList<>();
        siguienteId = 1;
    }

    public static SerieEntrenamientoRepository getInstance() {
        if (instance == null) {
            instance = new SerieEntrenamientoRepository();
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
        SerieEntrenamiento serie = new SerieEntrenamiento(
                siguienteId,
                idEntrenamiento,
                idRutinaEjercicio,
                numeroSerie,
                peso,
                repeticiones,
                completada
        );

        series.add(serie);
        siguienteId++;

        return serie;
    }

    public List<SerieEntrenamiento> obtenerPorEntrenamiento(
            int idEntrenamiento
    ) {
        List<SerieEntrenamiento> resultado =
                new ArrayList<>();

        for (SerieEntrenamiento serie : series) {
            if (serie.getIdEntrenamiento()
                    == idEntrenamiento) {

                resultado.add(serie);
            }
        }

        return resultado;
    }

    public List<SerieEntrenamiento> obtenerSeries() {
        return new ArrayList<>(series);
    }
}