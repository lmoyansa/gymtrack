package com.example.gymtrack.repository;

import com.example.gymtrack.data.GymTrackDatabase;
import com.example.gymtrack.data.dao.EjercicioDao;
import com.example.gymtrack.model.Ejercicio;

import java.util.List;

public class EjercicioRepository {

    private static EjercicioRepository instance;

    private final EjercicioDao ejercicioDao;

    private EjercicioRepository() {
        GymTrackDatabase database =
                GymTrackDatabase.obtenerInstancia();

        ejercicioDao = database.ejercicioDao();
    }

    public static EjercicioRepository getInstance() {
        if (instance == null) {
            instance = new EjercicioRepository();
        }

        return instance;
    }

    public Ejercicio crearEjercicio(
            String nombre,
            String grupoMuscular,
            String descripcion
    ) {
        Ejercicio ejercicio =
                new Ejercicio(
                        0,
                        nombre,
                        grupoMuscular,
                        descripcion
                );

        long idGenerado =
                ejercicioDao.insertar(ejercicio);

        ejercicio.setIdEjercicio(
                (int) idGenerado
        );

        return ejercicio;
    }

    public Ejercicio obtenerEjercicioPorId(
            int idEjercicio
    ) {
        return ejercicioDao.obtenerPorId(
                idEjercicio
        );
    }

    public List<Ejercicio> obtenerEjercicios() {
        return ejercicioDao.obtenerTodos();
    }

    public boolean editarEjercicio(
            int idEjercicio,
            String nombre,
            String grupoMuscular,
            String descripcion
    ) {
        Ejercicio ejercicio =
                obtenerEjercicioPorId(
                        idEjercicio
                );

        if (ejercicio == null) {
            return false;
        }

        ejercicio.setNombre(nombre);

        ejercicio.setGrupoMuscular(
                grupoMuscular
        );

        ejercicio.setDescripcion(
                descripcion
        );

        int filasActualizadas =
                ejercicioDao.actualizar(ejercicio);

        return filasActualizadas > 0;
    }

    public boolean eliminarEjercicio(
            int idEjercicio
    ) {
        int filasEliminadas =
                ejercicioDao.eliminarPorId(
                        idEjercicio
                );

        return filasEliminadas > 0;
    }
}