package com.example.gymtrack.repository;

import com.example.gymtrack.data.GymTrackDatabase;
import com.example.gymtrack.data.dao.RutinaEjercicioDao;
import com.example.gymtrack.model.RutinaEjercicio;

import java.util.List;

public class RutinaEjercicioRepository {

    private static RutinaEjercicioRepository instance;

    private final RutinaEjercicioDao
            rutinaEjercicioDao;

    private RutinaEjercicioRepository() {
        GymTrackDatabase database =
                GymTrackDatabase.obtenerInstancia();

        rutinaEjercicioDao =
                database.rutinaEjercicioDao();
    }

    public static RutinaEjercicioRepository
    getInstance() {

        if (instance == null) {
            instance =
                    new RutinaEjercicioRepository();
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
        int orden =
                obtenerPorRutina(idRutina)
                        .size() + 1;

        RutinaEjercicio rutinaEjercicio =
                new RutinaEjercicio(
                        0,
                        idRutina,
                        idEjercicio,
                        orden,
                        seriesPlanificadas,
                        repeticionesPlanificadas,
                        pesoObjetivo,
                        descansoSegundos
                );

        long idGenerado =
                rutinaEjercicioDao.insertar(
                        rutinaEjercicio
                );

        rutinaEjercicio.setIdRutinaEjercicio(
                (int) idGenerado
        );

        return rutinaEjercicio;
    }

    public RutinaEjercicio obtenerPorId(
            int idRutinaEjercicio
    ) {
        return rutinaEjercicioDao.obtenerPorId(
                idRutinaEjercicio
        );
    }

    public List<RutinaEjercicio> obtenerPorRutina(
            int idRutina
    ) {
        return rutinaEjercicioDao.obtenerPorRutina(
                idRutina
        );
    }

    public boolean editarRutinaEjercicio(
            int idRutinaEjercicio,
            int seriesPlanificadas,
            int repeticionesPlanificadas,
            double pesoObjetivo,
            int descansoSegundos
    ) {
        RutinaEjercicio rutinaEjercicio =
                obtenerPorId(
                        idRutinaEjercicio
                );

        if (rutinaEjercicio == null) {
            return false;
        }

        rutinaEjercicio.setSeriesPlanificadas(
                seriesPlanificadas
        );

        rutinaEjercicio
                .setRepeticionesPlanificadas(
                        repeticionesPlanificadas
                );

        rutinaEjercicio.setPesoObjetivo(
                pesoObjetivo
        );

        rutinaEjercicio.setDescansoSegundos(
                descansoSegundos
        );

        int filasActualizadas =
                rutinaEjercicioDao.actualizar(
                        rutinaEjercicio
                );

        return filasActualizadas > 0;
    }

    public boolean eliminarRutinaEjercicio(
            int idRutinaEjercicio
    ) {
        RutinaEjercicio rutinaEjercicio =
                obtenerPorId(
                        idRutinaEjercicio
                );

        if (rutinaEjercicio == null) {
            return false;
        }

        int idRutina =
                rutinaEjercicio.getIdRutina();

        int filasEliminadas =
                rutinaEjercicioDao.eliminarPorId(
                        idRutinaEjercicio
                );

        if (filasEliminadas <= 0) {
            return false;
        }

        reordenarEjercicios(idRutina);

        return true;
    }

    private void reordenarEjercicios(
            int idRutina
    ) {
        List<RutinaEjercicio> ejerciciosRutina =
                obtenerPorRutina(idRutina);

        for (int i = 0;
             i < ejerciciosRutina.size();
             i++) {

            RutinaEjercicio rutinaEjercicio =
                    ejerciciosRutina.get(i);

            int nuevoOrden = i + 1;

            if (rutinaEjercicio.getOrden()
                    != nuevoOrden) {

                rutinaEjercicio.setOrden(
                        nuevoOrden
                );

                rutinaEjercicioDao.actualizar(
                        rutinaEjercicio
                );
            }
        }
    }
}