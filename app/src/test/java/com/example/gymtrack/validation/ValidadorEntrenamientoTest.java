package com.example.gymtrack.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidadorEntrenamientoTest {

    @Test
    public void validarPeso_conValorPositivo_devuelveTrue() {
        assertTrue(
                ValidadorEntrenamiento.validarPeso(
                        80.5
                )
        );
    }

    @Test
    public void validarPeso_conCero_devuelveTrue() {
        assertTrue(
                ValidadorEntrenamiento.validarPeso(
                        0
                )
        );
    }

    @Test
    public void validarPeso_conValorNegativo_devuelveFalse() {
        assertFalse(
                ValidadorEntrenamiento.validarPeso(
                        -5
                )
        );
    }

    @Test
    public void validarRepeticiones_conValorPositivo_devuelveTrue() {
        assertTrue(
                ValidadorEntrenamiento
                        .validarRepeticiones(
                                10
                        )
        );
    }

    @Test
    public void validarRepeticiones_conCero_devuelveFalse() {
        assertFalse(
                ValidadorEntrenamiento
                        .validarRepeticiones(
                                0
                        )
        );
    }

    @Test
    public void validarRepeticiones_conValorNegativo_devuelveFalse() {
        assertFalse(
                ValidadorEntrenamiento
                        .validarRepeticiones(
                                -3
                        )
        );
    }

    @Test
    public void validarSerieCompletada_conDatosValidos_devuelveTrue() {
        assertTrue(
                ValidadorEntrenamiento.validarSerie(
                        true,
                        "80",
                        "10",
                        80,
                        10
                )
        );
    }

    @Test
    public void validarSerieCompletada_sinPeso_devuelveFalse() {
        assertFalse(
                ValidadorEntrenamiento.validarSerie(
                        true,
                        "",
                        "10",
                        80,
                        10
                )
        );
    }

    @Test
    public void validarSerieCompletada_sinRepeticiones_devuelveFalse() {
        assertFalse(
                ValidadorEntrenamiento.validarSerie(
                        true,
                        "80",
                        "",
                        80,
                        10
                )
        );
    }

    @Test
    public void validarSerieNoCompletada_sinTextosConValoresPlanificados_devuelveTrue() {
        assertTrue(
                ValidadorEntrenamiento.validarSerie(
                        false,
                        "",
                        "",
                        80,
                        10
                )
        );
    }
}