package com.example.gymtrack.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RutinaRepositoryTest {

    @Test
    public void validarNombreRutina_conNombreValido_devuelveTrue() {
        boolean resultado =
                RutinaRepository.validarNombreRutina(
                        "Pecho y tríceps"
                );

        assertTrue(resultado);
    }

    @Test
    public void validarNombreRutina_conNombreVacio_devuelveFalse() {
        boolean resultado =
                RutinaRepository.validarNombreRutina("");

        assertFalse(resultado);
    }

    @Test
    public void validarNombreRutina_conEspacios_devuelveFalse() {
        boolean resultado =
                RutinaRepository.validarNombreRutina("   ");

        assertFalse(resultado);
    }

    @Test
    public void validarNombreRutina_conValorNulo_devuelveFalse() {
        boolean resultado =
                RutinaRepository.validarNombreRutina(null);

        assertFalse(resultado);
    }
}