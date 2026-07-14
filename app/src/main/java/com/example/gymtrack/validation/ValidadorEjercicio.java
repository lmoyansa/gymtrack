package com.example.gymtrack.validation;

public final class ValidadorEjercicio {

    private ValidadorEjercicio() {
    }

    public static boolean validarNombreEjercicio(
            String nombre
    ) {
        return nombre != null
                && !nombre.trim().isEmpty();
    }
}