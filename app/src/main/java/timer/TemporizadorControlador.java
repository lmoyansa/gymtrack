package com.example.gymtrack.timer;

public class TemporizadorControlador {

    public static final long TIEMPO_60_SEGUNDOS =
            60_000L;

    public static final long TIEMPO_90_SEGUNDOS =
            90_000L;

    public static final long TIEMPO_120_SEGUNDOS =
            120_000L;

    private long tiempoSeleccionado;
    private long tiempoRestante;
    private boolean temporizadorEnMarcha;

    public TemporizadorControlador() {
        this(
                TIEMPO_90_SEGUNDOS,
                TIEMPO_90_SEGUNDOS,
                false
        );
    }

    public TemporizadorControlador(
            long tiempoSeleccionado,
            long tiempoRestante,
            boolean temporizadorEnMarcha
    ) {
        if (esTiempoPredefinido(tiempoSeleccionado)) {
            this.tiempoSeleccionado =
                    tiempoSeleccionado;
        } else {
            this.tiempoSeleccionado =
                    TIEMPO_90_SEGUNDOS;
        }

        this.tiempoRestante =
                Math.max(
                        0L,
                        tiempoRestante
                );

        this.temporizadorEnMarcha =
                temporizadorEnMarcha
                        && this.tiempoRestante > 0;
    }

    public boolean seleccionarTiempo(
            long tiempo
    ) {
        if (!esTiempoPredefinido(tiempo)) {
            return false;
        }

        tiempoSeleccionado = tiempo;
        tiempoRestante = tiempo;
        temporizadorEnMarcha = false;

        return true;
    }

    public void iniciar() {
        if (tiempoRestante <= 0) {
            tiempoRestante =
                    tiempoSeleccionado;
        }

        temporizadorEnMarcha = true;
    }

    public void pausar() {
        temporizadorEnMarcha = false;
    }

    public void reiniciar() {
        tiempoRestante =
                tiempoSeleccionado;

        temporizadorEnMarcha = false;
    }

    public void preparar() {
        temporizadorEnMarcha = false;

        if (tiempoRestante <= 0) {
            tiempoRestante =
                    tiempoSeleccionado;
        }
    }

    public void actualizarTiempoRestante(
            long tiempoRestante
    ) {
        this.tiempoRestante =
                Math.max(
                        0L,
                        tiempoRestante
                );

        if (this.tiempoRestante == 0) {
            temporizadorEnMarcha = false;
        }
    }

    public void finalizar() {
        tiempoRestante = 0L;
        temporizadorEnMarcha = false;
    }

    public long getTiempoSeleccionado() {
        return tiempoSeleccionado;
    }

    public long getTiempoRestante() {
        return tiempoRestante;
    }

    public boolean isTemporizadorEnMarcha() {
        return temporizadorEnMarcha;
    }

    public static boolean esTiempoPredefinido(
            long tiempo
    ) {
        return tiempo == TIEMPO_60_SEGUNDOS
                || tiempo == TIEMPO_90_SEGUNDOS
                || tiempo == TIEMPO_120_SEGUNDOS;
    }
}