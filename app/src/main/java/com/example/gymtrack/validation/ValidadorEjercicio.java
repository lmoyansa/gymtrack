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

    public static boolean validarSeries(
            int series
    ) {
        return series > 0;
    }

    public static boolean validarRepeticiones(
            int repeticiones
    ) {
        return repeticiones > 0;
    }

    public static boolean validarPeso(
            double peso
    ) {
        return peso >= 0;
    }
}