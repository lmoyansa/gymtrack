package com.example.gymtrack.repository;

import com.example.gymtrack.data.GymTrackDatabase;
import com.example.gymtrack.data.dao.RutinaDao;
import com.example.gymtrack.model.Rutina;

import java.util.Date;
import java.util.List;

public class RutinaRepository {

    private static RutinaRepository instance;

    private final RutinaDao rutinaDao;

    private RutinaRepository() {
        GymTrackDatabase database =
                GymTrackDatabase.obtenerInstancia();

        rutinaDao = database.rutinaDao();
    }

    public static RutinaRepository getInstance() {
        if (instance == null) {
            instance = new RutinaRepository();
        }

        return instance;
    }

    public List<Rutina> obtenerRutinas() {
        return rutinaDao.obtenerTodas();
    }

    public Rutina obtenerRutinaPorId(
            int idRutina
    ) {
        return rutinaDao.obtenerPorId(idRutina);
    }

    public Rutina crearRutina(
            String nombre,
            String descripcion,
            String objetivo
    ) {
        Rutina nuevaRutina =
                new Rutina(
                        0,
                        1,
                        nombre,
                        descripcion,
                        objetivo,
                        new Date(),
                        true
                );

        long idGenerado =
                rutinaDao.insertar(nuevaRutina);

        nuevaRutina.setIdRutina(
                (int) idGenerado
        );

        return nuevaRutina;
    }

    public boolean editarRutina(
            int idRutina,
            String nombre,
            String descripcion,
            String objetivo
    ) {
        Rutina rutina =
                obtenerRutinaPorId(idRutina);

        if (rutina == null) {
            return false;
        }

        rutina.setNombre(nombre);
        rutina.setDescripcion(descripcion);
        rutina.setObjetivo(objetivo);

        int filasActualizadas =
                rutinaDao.actualizar(rutina);

        return filasActualizadas > 0;
    }

    public boolean eliminarRutina(
            int idRutina
    ) {
        Rutina rutina =
                obtenerRutinaPorId(idRutina);

        if (rutina == null) {
            return false;
        }

        int filasEliminadas =
                rutinaDao.eliminarPorId(idRutina);

        return filasEliminadas > 0;
    }

    public boolean validarNombreRutina(
            String nombre
    ) {
        return nombre != null
                && !nombre.trim().isEmpty();
    }
}