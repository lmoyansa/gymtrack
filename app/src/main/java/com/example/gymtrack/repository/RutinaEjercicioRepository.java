package com.example.gymtrack.repository;

import com.example.gymtrack.model.RutinaEjercicio;

import java.util.ArrayList;
import java.util.List;

public class RutinaEjercicioRepository {

    private static RutinaEjercicioRepository instance;

    private final List<RutinaEjercicio> rutinaEjercicios;
    private int siguienteId;

    private RutinaEjercicioRepository() {
        rutinaEjercicios = new ArrayList<>();
        siguienteId = 1;
    }

    public static RutinaEjercicioRepository getInstance() {
        if (instance == null) {
            instance = new RutinaEjercicioRepository();
        }

        return instance;
    }

    public RutinaEjercicio crearRutinaEjercicio(
            int idRutina,
            int idEjercicio,
            int seriesPlanificadas,
            int repeticionesPlanificadas,
            double pesoObjetivo,
            int descansoSegundos
    ) {
        int orden = obtenerPorRutina(idRutina).size() + 1;

        RutinaEjercicio rutinaEjercicio = new RutinaEjercicio(
                siguienteId,
                idRutina,
                idEjercicio,
                orden,
                seriesPlanificadas,
                repeticionesPlanificadas,
                pesoObjetivo,
                descansoSegundos
        );

        rutinaEjercicios.add(rutinaEjercicio);
        siguienteId++;

        return rutinaEjercicio;
    }

    public List<RutinaEjercicio> obtenerPorRutina(int idRutina) {
        List<RutinaEjercicio> resultado = new ArrayList<>();

        for (RutinaEjercicio rutinaEjercicio : rutinaEjercicios) {
            if (rutinaEjercicio.getIdRutina() == idRutina) {
                resultado.add(rutinaEjercicio);
            }
        }

        return resultado;
    }
}