package com.example.gymtrack.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidadorEjercicioTest {

    @Test
    public void validarNombreEjercicio_conNombreValido_devuelveTrue() {
        boolean resultado =
                ValidadorEjercicio
                        .validarNombreEjercicio(
                                "Press banca"
                        );

        assertTrue(resultado);
    }

    @Test
    public void validarNombreEjercicio_conNombreVacio_devuelveFalse() {
        boolean resultado =
                ValidadorEjercicio
                        .validarNombreEjercicio("");

        assertFalse(resultado);
    }

    @Test
    public void validarNombreEjercicio_conEspacios_devuelveFalse() {
        boolean resultado =
                ValidadorEjercicio
                        .validarNombreEjercicio("   ");

        assertFalse(resultado);
    }

    @Test
    public void validarNombreEjercicio_conValorNulo_devuelveFalse() {
        boolean resultado =
                ValidadorEjercicio
                        .validarNombreEjercicio(null);

        assertFalse(resultado);
    }
}