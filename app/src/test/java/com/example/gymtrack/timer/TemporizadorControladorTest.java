package com.example.gymtrack.timer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TemporizadorControladorTest {

    @Test
    public void estadoInicial_usa90SegundosYEstaPausado() {
        TemporizadorControlador controlador =
                new TemporizadorControlador();

        assertEquals(
                TemporizadorControlador
                        .TIEMPO_90_SEGUNDOS,
                controlador.getTiempoSeleccionado()
        );

        assertEquals(
                TemporizadorControlador
                        .TIEMPO_90_SEGUNDOS,
                controlador.getTiempoRestante()
        );

        assertFalse(
                controlador.isTemporizadorEnMarcha()
        );
    }

    @Test
    public void seleccionar60Segundos_actualizaElTiempo() {
        TemporizadorControlador controlador =
                new TemporizadorControlador();

        assertTrue(
                controlador.seleccionarTiempo(
                        TemporizadorControlador
                                .TIEMPO_60_SEGUNDOS
                )
        );

        assertEquals(
                60_000L,
                controlador.getTiempoRestante()
        );
    }

    @Test
    public void seleccionar90Segundos_actualizaElTiempo() {
        TemporizadorControlador controlador =
                new TemporizadorControlador();

        assertTrue(
                controlador.seleccionarTiempo(
                        TemporizadorControlador
                                .TIEMPO_90_SEGUNDOS
                )
        );

        assertEquals(
                90_000L,
                controlador.getTiempoRestante()
        );
    }

    @Test
    public void seleccionar120Segundos_actualizaElTiempo() {
        TemporizadorControlador controlador =
                new TemporizadorControlador();

        assertTrue(
                controlador.seleccionarTiempo(
                        TemporizadorControlador
                                .TIEMPO_120_SEGUNDOS
                )
        );

        assertEquals(
                120_000L,
                controlador.getTiempoRestante()
        );
    }

    @Test
    public void iniciarTemporizador_cambiaElEstadoAEnMarcha() {
        TemporizadorControlador controlador =
                new TemporizadorControlador();

        controlador.iniciar();

        assertTrue(
                controlador.isTemporizadorEnMarcha()
        );
    }

    @Test
    public void pausarTemporizador_conservaElTiempoRestante() {
        TemporizadorControlador controlador =
                new TemporizadorControlador();

        controlador.iniciar();

        controlador.actualizarTiempoRestante(
                45_000L
        );

        controlador.pausar();

        assertFalse(
                controlador.isTemporizadorEnMarcha()
        );

        assertEquals(
                45_000L,
                controlador.getTiempoRestante()
        );
    }

    @Test
    public void reiniciarTemporizador_recuperaElTiempoSeleccionado() {
        TemporizadorControlador controlador =
                new TemporizadorControlador();

        controlador.seleccionarTiempo(
                TemporizadorControlador
                        .TIEMPO_120_SEGUNDOS
        );

        controlador.iniciar();

        controlador.actualizarTiempoRestante(
                30_000L
        );

        controlador.reiniciar();

        assertEquals(
                120_000L,
                controlador.getTiempoRestante()
        );

        assertFalse(
                controlador.isTemporizadorEnMarcha()
        );
    }

    @Test
    public void finalizarTemporizador_dejaElTiempoEnCero() {
        TemporizadorControlador controlador =
                new TemporizadorControlador();

        controlador.iniciar();
        controlador.finalizar();

        assertEquals(
                0L,
                controlador.getTiempoRestante()
        );

        assertFalse(
                controlador.isTemporizadorEnMarcha()
        );
    }
}