package com.example.gymtrack.timer;

public class TemporizadorEstado {

    public static final long TIEMPO_60_SEGUNDOS =
            60_000L;

    public static final long TIEMPO_90_SEGUNDOS =
            90_000L;

    public static final long TIEMPO_120_SEGUNDOS =
            120_000L;

    private long tiempoSeleccionado =
            TIEMPO_90_SEGUNDOS;

    private long tiempoRestante =
            TIEMPO_90_SEGUNDOS;

    private boolean enMarcha;

    public boolean seleccionarTiempo(
            long tiempo
    ) {
        if (!esTiempoPredefinido(tiempo)) {
            return false;
        }

        tiempoSeleccionado = tiempo;
        tiempoRestante = tiempo;
        enMarcha = false;

        return true;
    }

    public void iniciar() {
        if (tiempoRestante <= 0) {
            tiempoRestante =
                    tiempoSeleccionado;
        }

        enMarcha = true;
    }

    public void pausar() {
        enMarcha = false;
    }

    public void reiniciar() {
        tiempoRestante =
                tiempoSeleccionado;

        enMarcha = false;
    }

    public void finalizar() {
        tiempoRestante = 0;
        enMarcha = false;
    }

    public void actualizarTiempoRestante(
            long tiempoRestante
    ) {
        this.tiempoRestante =
                Math.max(
                        0,
                        tiempoRestante
                );
    }

    public void restaurarEstado(
            long tiempoSeleccionado,
            long tiempoRestante,
            boolean enMarcha
    ) {
        if (!esTiempoPredefinido(
                tiempoSeleccionado
        )) {
            tiempoSeleccionado =
                    TIEMPO_90_SEGUNDOS;
        }

        this.tiempoSeleccionado =
                tiempoSeleccionado;

        this.tiempoRestante =
                Math.min(
                        tiempoSeleccionado,
                        Math.max(
                                0,
                                tiempoRestante
                        )
                );

        this.enMarcha =
                enMarcha
                        && this.tiempoRestante > 0;
    }

    public long getTiempoSeleccionado() {
        return tiempoSeleccionado;
    }

    public long getTiempoRestante() {
        return tiempoRestante;
    }

    public boolean estaEnMarcha() {
        return enMarcha;
    }

    private boolean esTiempoPredefinido(
            long tiempo
    ) {
        return tiempo == TIEMPO_60_SEGUNDOS
                || tiempo == TIEMPO_90_SEGUNDOS
                || tiempo == TIEMPO_120_SEGUNDOS;
    }
}