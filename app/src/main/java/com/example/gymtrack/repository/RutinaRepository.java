package com.example.gymtrack.repository;

import com.example.gymtrack.model.Rutina;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RutinaRepository {

    private static RutinaRepository instance;
    private final List<Rutina> rutinas;
    private int siguienteId;

    private RutinaRepository() {
        rutinas = new ArrayList<>();
        siguienteId = 1;
    }

    public static RutinaRepository getInstance() {
        if (instance == null) {
            instance = new RutinaRepository();
        }
        return instance;
    }

    public List<Rutina> obtenerRutinas() {
        return rutinas;
    }

    public Rutina obtenerRutinaPorId(int idRutina) {
        for (Rutina rutina : rutinas) {
            if (rutina.getIdRutina() == idRutina) {
                return rutina;
            }
        }
        return null;
    }

    public Rutina crearRutina(String nombre, String descripcion, String objetivo) {
        Rutina nuevaRutina = new Rutina(
                siguienteId,
                1,
                nombre,
                descripcion,
                objetivo,
                new Date(),
                true
        );

        rutinas.add(nuevaRutina);
        siguienteId++;

        return nuevaRutina;
    }

    public boolean editarRutina(int idRutina, String nombre, String descripcion, String objetivo) {
        Rutina rutina = obtenerRutinaPorId(idRutina);

        if (rutina == null) {
            return false;
        }

        rutina.setNombre(nombre);
        rutina.setDescripcion(descripcion);
        rutina.setObjetivo(objetivo);

        return true;
    }

    public boolean eliminarRutina(int idRutina) {
        Rutina rutina = obtenerRutinaPorId(idRutina);

        if (rutina == null) {
            return false;
        }

        rutinas.remove(rutina);
        return true;
    }

    public boolean validarNombreRutina(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }
}