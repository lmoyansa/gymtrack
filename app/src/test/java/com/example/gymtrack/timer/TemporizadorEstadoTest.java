package com.example.gymtrack.timer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TemporizadorEstadoTest {

    @Test
    public void estadoInicial_utilizaNoventaSegundos() {
        TemporizadorEstado temporizador =
                new TemporizadorEstado();

        assertEquals(
                TemporizadorEstado.TIEMPO_90_SEGUNDOS,
                temporizador.getTiempoSeleccionado()
        );

        assertEquals(
                TemporizadorEstado.TIEMPO_90_SEGUNDOS,
                temporizador.getTiempoRestante()
        );

        assertFalse(
                temporizador.estaEnMarcha()
        );
    }

    @Test
    public void seleccionarTiempo_sesentaSegundos_actualizaEstado() {
        TemporizadorEstado temporizador =
                new TemporizadorEstado();

        boolean resultado =
                temporizador.seleccionarTiempo(
                        TemporizadorEstado
                                .TIEMPO_60_SEGUNDOS
                );

        assertTrue(resultado);

        assertEquals(
                TemporizadorEstado.TIEMPO_60_SEGUNDOS,
                temporizador.getTiempoRestante()
        );
    }

    @Test
    public void seleccionarTiempo_noventaSegundos_actualizaEstado() {
        TemporizadorEstado temporizador =
                new TemporizadorEstado();

        boolean resultado =
                temporizador.seleccionarTiempo(
                        TemporizadorEstado
                                .TIEMPO_90_SEGUNDOS
                );

        assertTrue(resultado);

        assertEquals(
                TemporizadorEstado.TIEMPO_90_SEGUNDOS,
                temporizador.getTiempoRestante()
        );
    }

    @Test
    public void seleccionarTiempo_cientoVeinteSegundos_actualizaEstado() {
        TemporizadorEstado temporizador =
                new TemporizadorEstado();

        boolean resultado =
                temporizador.seleccionarTiempo(
                        TemporizadorEstado
                                .TIEMPO_120_SEGUNDOS
                );

        assertTrue(resultado);

        assertEquals(
                TemporizadorEstado.TIEMPO_120_SEGUNDOS,
                temporizador.getTiempoRestante()
        );
    }

    @Test
    public void seleccionarTiempo_noPredefinido_devuelveFalse() {
        TemporizadorEstado temporizador =
                new TemporizadorEstado();

        boolean resultado =
                temporizador.seleccionarTiempo(
                        30_000L
                );

        assertFalse(resultado);

        assertEquals(
                TemporizadorEstado.TIEMPO_90_SEGUNDOS,
                temporizador.getTiempoSeleccionado()
        );
    }

    @Test
    public void iniciarTemporizador_cambiaEstadoAEnMarcha() {
        TemporizadorEstado temporizador =
                new TemporizadorEstado();

        temporizador.iniciar();

        assertTrue(
                temporizador.estaEnMarcha()
        );
    }

    @Test
    public void pausarTemporizador_conservaTiempoRestante() {
        TemporizadorEstado temporizador =
                new TemporizadorEstado();

        temporizador.iniciar();

        temporizador.actualizarTiempoRestante(
                45_000L
        );

        temporizador.pausar();

        assertFalse(
                temporizador.estaEnMarcha()
        );

        assertEquals(
                45_000L,
                temporizador.getTiempoRestante()
        );
    }

    @Test
    public void reiniciarTemporizador_recuperaTiempoSeleccionado() {
        TemporizadorEstado temporizador =
                new TemporizadorEstado();

        temporizador.seleccionarTiempo(
                TemporizadorEstado
                        .TIEMPO_120_SEGUNDOS
        );

        temporizador.iniciar();

        temporizador.actualizarTiempoRestante(
                40_000L
        );

        temporizador.reiniciar();

        assertFalse(
                temporizador.estaEnMarcha()
        );

        assertEquals(
                TemporizadorEstado.TIEMPO_120_SEGUNDOS,
                temporizador.getTiempoRestante()
        );
    }
}