package com.example.gymtrack.validation;

public final class ValidadorEntrenamiento {

    private ValidadorEntrenamiento() {
    }

    public static boolean validarPeso(
            double peso
    ) {
        return peso >= 0;
    }

    public static boolean validarRepeticiones(
            int repeticiones
    ) {
        return repeticiones > 0;
    }

    public static boolean tieneDatosObligatorios(
            boolean completada,
            String textoPeso,
            String textoRepeticiones
    ) {
        if (!completada) {
            return true;
        }

        return textoPeso != null
                && !textoPeso.trim().isEmpty()
                && textoRepeticiones != null
                && !textoRepeticiones.trim().isEmpty();
    }

    public static boolean validarSerie(
            boolean completada,
            String textoPeso,
            String textoRepeticiones,
            double peso,
            int repeticiones
    ) {
        return tieneDatosObligatorios(
                completada,
                textoPeso,
                textoRepeticiones
        )
                && validarPeso(peso)
                && validarRepeticiones(
                repeticiones
        );
    }
}