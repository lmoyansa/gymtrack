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

    public RutinaEjercicio obtenerPorId(
            int idRutinaEjercicio
    ) {
        for (RutinaEjercicio rutinaEjercicio : rutinaEjercicios) {
            if (rutinaEjercicio.getIdRutinaEjercicio()
                    == idRutinaEjercicio) {

                return rutinaEjercicio;
            }
        }

        return null;
    }

    public List<RutinaEjercicio> obtenerPorRutina(
            int idRutina
    ) {
        List<RutinaEjercicio> resultado = new ArrayList<>();

        for (RutinaEjercicio rutinaEjercicio : rutinaEjercicios) {
            if (rutinaEjercicio.getIdRutina() == idRutina) {
                resultado.add(rutinaEjercicio);
            }
        }

        return resultado;
    }

    public boolean editarRutinaEjercicio(
            int idRutinaEjercicio,
            int seriesPlanificadas,
            int repeticionesPlanificadas,
            double pesoObjetivo,
            int descansoSegundos
    ) {
        RutinaEjercicio rutinaEjercicio =
                obtenerPorId(idRutinaEjercicio);

        if (rutinaEjercicio == null) {
            return false;
        }

        rutinaEjercicio.setSeriesPlanificadas(
                seriesPlanificadas
        );

        rutinaEjercicio.setRepeticionesPlanificadas(
                repeticionesPlanificadas
        );

        rutinaEjercicio.setPesoObjetivo(pesoObjetivo);
        rutinaEjercicio.setDescansoSegundos(descansoSegundos);

        return true;
    }

    public boolean eliminarRutinaEjercicio(
            int idRutinaEjercicio
    ) {
        for (int i = 0; i < rutinaEjercicios.size(); i++) {
            RutinaEjercicio rutinaEjercicio =
                    rutinaEjercicios.get(i);

            if (rutinaEjercicio.getIdRutinaEjercicio()
                    == idRutinaEjercicio) {

                int idRutina =
                        rutinaEjercicio.getIdRutina();

                rutinaEjercicios.remove(i);
                reordenarEjercicios(idRutina);

                return true;
            }
        }

        return false;
    }

    private void reordenarEjercicios(int idRutina) {
        List<RutinaEjercicio> ejerciciosRutina =
                obtenerPorRutina(idRutina);

        for (int i = 0; i < ejerciciosRutina.size(); i++) {
            ejerciciosRutina.get(i).setOrden(i + 1);
        }
    }
}