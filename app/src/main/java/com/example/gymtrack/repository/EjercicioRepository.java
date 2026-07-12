package com.example.gymtrack.repository;

import com.example.gymtrack.model.Ejercicio;

import java.util.ArrayList;
import java.util.List;

public class EjercicioRepository {

    private static EjercicioRepository instance;

    private final List<Ejercicio> ejercicios;
    private int siguienteId;

    private EjercicioRepository() {
        ejercicios = new ArrayList<>();
        siguienteId = 1;
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
        Ejercicio ejercicio = new Ejercicio(
                siguienteId,
                nombre,
                grupoMuscular,
                descripcion
        );

        ejercicios.add(ejercicio);
        siguienteId++;

        return ejercicio;
    }

    public Ejercicio obtenerEjercicioPorId(int idEjercicio) {
        for (Ejercicio ejercicio : ejercicios) {
            if (ejercicio.getIdEjercicio() == idEjercicio) {
                return ejercicio;
            }
        }

        return null;
    }

    public List<Ejercicio> obtenerEjercicios() {
        return ejercicios;
    }

    public boolean editarEjercicio(
            int idEjercicio,
            String nombre,
            String grupoMuscular,
            String descripcion
    ) {
        Ejercicio ejercicio =
                obtenerEjercicioPorId(idEjercicio);

        if (ejercicio == null) {
            return false;
        }

        ejercicio.setNombre(nombre);
        ejercicio.setGrupoMuscular(grupoMuscular);
        ejercicio.setDescripcion(descripcion);

        return true;
    }

    public boolean eliminarEjercicio(int idEjercicio) {
        for (int i = 0; i < ejercicios.size(); i++) {
            Ejercicio ejercicio = ejercicios.get(i);

            if (ejercicio.getIdEjercicio() == idEjercicio) {
                ejercicios.remove(i);
                return true;
            }
        }

        return false;
    }
}