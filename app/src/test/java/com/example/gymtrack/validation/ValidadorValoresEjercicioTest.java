package com.example.gymtrack.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidadorValoresEjercicioTest {

    @Test
    public void validarSeries_conValorPositivo_devuelveTrue() {
        assertTrue(
                ValidadorEjercicio.validarSeries(4)
        );
    }

    @Test
    public void validarSeries_conCero_devuelveFalse() {
        assertFalse(
                ValidadorEjercicio.validarSeries(0)
        );
    }

    @Test
    public void validarSeries_conValorNegativo_devuelveFalse() {
        assertFalse(
                ValidadorEjercicio.validarSeries(-1)
        );
    }

    @Test
    public void validarRepeticiones_conValorPositivo_devuelveTrue() {
        assertTrue(
                ValidadorEjercicio.validarRepeticiones(10)
        );
    }

    @Test
    public void validarRepeticiones_conCero_devuelveFalse() {
        assertFalse(
                ValidadorEjercicio.validarRepeticiones(0)
        );
    }

    @Test
    public void validarRepeticiones_conValorNegativo_devuelveFalse() {
        assertFalse(
                ValidadorEjercicio.validarRepeticiones(-5)
        );
    }

    @Test
    public void validarPeso_conValorPositivo_devuelveTrue() {
        assertTrue(
                ValidadorEjercicio.validarPeso(80.5)
        );
    }

    @Test
    public void validarPeso_conCero_devuelveTrue() {
        assertTrue(
                ValidadorEjercicio.validarPeso(0)
        );
    }

    @Test
    public void validarPeso_conValorNegativo_devuelveFalse() {
        assertFalse(
                ValidadorEjercicio.validarPeso(-2.5)
        );
    }
}