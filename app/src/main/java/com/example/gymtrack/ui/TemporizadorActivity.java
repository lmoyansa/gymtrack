package com.example.gymtrack.ui;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.timer.TemporizadorControlador;

import java.util.Locale;

public class TemporizadorActivity extends Activity {

    private static final String ESTADO_TIEMPO_SELECCIONADO =
            "tiempoSeleccionado";

    private static final String ESTADO_TIEMPO_RESTANTE =
            "tiempoRestante";

    private static final String ESTADO_EN_MARCHA =
            "temporizadorEnMarcha";

    private TextView tvTiempoTemporizador;
    private TextView tvEstadoTemporizador;

    private Button btnTiempo60;
    private Button btnTiempo90;
    private Button btnTiempo120;
    private Button btnIniciarPausarTemporizador;

    private CountDownTimer countDownTimer;

    private TemporizadorControlador
            temporizadorControlador;

    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_temporizador
        );

        tvTiempoTemporizador =
                findViewById(
                        R.id.tvTiempoTemporizador
                );

        tvEstadoTemporizador =
                findViewById(
                        R.id.tvEstadoTemporizador
                );

        btnTiempo60 =
                findViewById(
                        R.id.btnTiempo60
                );

        btnTiempo90 =
                findViewById(
                        R.id.btnTiempo90
                );

        btnTiempo120 =
                findViewById(
                        R.id.btnTiempo120
                );

        btnIniciarPausarTemporizador =
                findViewById(
                        R.id.btnIniciarPausarTemporizador
                );

        Button btnReiniciarTemporizador =
                findViewById(
                        R.id.btnReiniciarTemporizador
                );

        Button btnVolverInicioTemporizador =
                findViewById(
                        R.id.btnVolverInicioTemporizador
                );

        boolean reanudarTemporizador = false;

        if (savedInstanceState != null) {
            long tiempoSeleccionado =
                    savedInstanceState.getLong(
                            ESTADO_TIEMPO_SELECCIONADO,
                            TemporizadorControlador
                                    .TIEMPO_90_SEGUNDOS
                    );

            long tiempoRestante =
                    savedInstanceState.getLong(
                            ESTADO_TIEMPO_RESTANTE,
                            tiempoSeleccionado
                    );

            reanudarTemporizador =
                    savedInstanceState.getBoolean(
                            ESTADO_EN_MARCHA,
                            false
                    );

            temporizadorControlador =
                    new TemporizadorControlador(
                            tiempoSeleccionado,
                            tiempoRestante,
                            reanudarTemporizador
                    );

        } else {
            temporizadorControlador =
                    new TemporizadorControlador();
        }

        btnTiempo60.setOnClickListener(
                v -> seleccionarTiempo(
                        TemporizadorControlador
                                .TIEMPO_60_SEGUNDOS
                )
        );

        btnTiempo90.setOnClickListener(
                v -> seleccionarTiempo(
                        TemporizadorControlador
                                .TIEMPO_90_SEGUNDOS
                )
        );

        btnTiempo120.setOnClickListener(
                v -> seleccionarTiempo(
                        TemporizadorControlador
                                .TIEMPO_120_SEGUNDOS
                )
        );

        btnIniciarPausarTemporizador
                .setOnClickListener(
                        v -> {
                            if (temporizadorControlador
                                    .isTemporizadorEnMarcha()) {

                                pausarTemporizador();

                            } else {
                                iniciarTemporizador();
                            }
                        }
                );

        btnReiniciarTemporizador
                .setOnClickListener(
                        v -> reiniciarTemporizador()
                );

        btnVolverInicioTemporizador
                .setOnClickListener(
                        v -> finish()
                );

        actualizarTiempoMostrado();
        actualizarBotonesRapidos();

        if (reanudarTemporizador
                && temporizadorControlador
                .getTiempoRestante() > 0) {

            iniciarTemporizador();

        } else {
            actualizarEstadoInicial();
        }
    }

    private void seleccionarTiempo(
            long tiempo
    ) {
        cancelarCuentaAtras();

        boolean tiempoValido =
                temporizadorControlador
                        .seleccionarTiempo(
                                tiempo
                        );

        if (!tiempoValido) {
            return;
        }

        tvEstadoTemporizador.setText(
                "TIEMPO SELECCIONADO"
        );

        btnIniciarPausarTemporizador.setText(
                "Iniciar"
        );

        actualizarTiempoMostrado();
        actualizarBotonesRapidos();
    }

    private void iniciarTemporizador() {
        temporizadorControlador.iniciar();

        tvEstadoTemporizador.setText(
                "DESCANSANDO"
        );

        btnIniciarPausarTemporizador.setText(
                "Pausar"
        );

        countDownTimer =
                new CountDownTimer(
                        temporizadorControlador
                                .getTiempoRestante(),
                        1_000L
                ) {
                    @Override
                    public void onTick(
                            long millisHastaFinalizar
                    ) {
                        temporizadorControlador
                                .actualizarTiempoRestante(
                                        millisHastaFinalizar
                                );

                        actualizarTiempoMostrado();
                    }

                    @Override
                    public void onFinish() {
                        temporizadorControlador
                                .finalizar();

                        actualizarTiempoMostrado();

                        tvEstadoTemporizador.setText(
                                "DESCANSO TERMINADO"
                        );

                        btnIniciarPausarTemporizador
                                .setText(
                                        "Repetir"
                                );

                        avisarFinTemporizador();
                    }
                }.start();
    }

    private void pausarTemporizador() {
        cancelarCuentaAtras();

        temporizadorControlador.pausar();

        tvEstadoTemporizador.setText(
                "TEMPORIZADOR PAUSADO"
        );

        btnIniciarPausarTemporizador.setText(
                "Continuar"
        );
    }

    private void reiniciarTemporizador() {
        cancelarCuentaAtras();

        temporizadorControlador.reiniciar();

        tvEstadoTemporizador.setText(
                "LISTO PARA EMPEZAR"
        );

        btnIniciarPausarTemporizador.setText(
                "Iniciar"
        );

        actualizarTiempoMostrado();
    }

    private void actualizarEstadoInicial() {
        temporizadorControlador.preparar();

        tvEstadoTemporizador.setText(
                "LISTO PARA EMPEZAR"
        );

        btnIniciarPausarTemporizador.setText(
                "Iniciar"
        );

        actualizarTiempoMostrado();
    }

    private void actualizarTiempoMostrado() {
        long segundosTotales =
                (
                        temporizadorControlador
                                .getTiempoRestante()
                                + 999L
                ) / 1_000L;

        long minutos =
                segundosTotales / 60L;

        long segundos =
                segundosTotales % 60L;

        String tiempoFormateado =
                String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        minutos,
                        segundos
                );

        tvTiempoTemporizador.setText(
                tiempoFormateado
        );
    }

    private void actualizarBotonesRapidos() {
        long tiempoSeleccionado =
                temporizadorControlador
                        .getTiempoSeleccionado();

        btnTiempo60.setAlpha(
                tiempoSeleccionado
                        == TemporizadorControlador
                        .TIEMPO_60_SEGUNDOS
                        ? 1.0f
                        : 0.6f
        );

        btnTiempo90.setAlpha(
                tiempoSeleccionado
                        == TemporizadorControlador
                        .TIEMPO_90_SEGUNDOS
                        ? 1.0f
                        : 0.6f
        );

        btnTiempo120.setAlpha(
                tiempoSeleccionado
                        == TemporizadorControlador
                        .TIEMPO_120_SEGUNDOS
                        ? 1.0f
                        : 0.6f
        );
    }

    private void avisarFinTemporizador() {
        Toast.makeText(
                this,
                "Descanso terminado. Continúa con la siguiente serie.",
                Toast.LENGTH_LONG
        ).show();

        try {
            Uri sonidoNotificacion =
                    RingtoneManager.getDefaultUri(
                            RingtoneManager
                                    .TYPE_NOTIFICATION
                    );

            if (sonidoNotificacion == null) {
                return;
            }

            Ringtone ringtone =
                    RingtoneManager.getRingtone(
                            getApplicationContext(),
                            sonidoNotificacion
                    );

            if (ringtone != null) {
                ringtone.play();
            }

        } catch (Exception exception) {
            Toast.makeText(
                    this,
                    "Descanso terminado",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void cancelarCuentaAtras() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    protected void onSaveInstanceState(
            Bundle outState
    ) {
        super.onSaveInstanceState(outState);

        outState.putLong(
                ESTADO_TIEMPO_SELECCIONADO,
                temporizadorControlador
                        .getTiempoSeleccionado()
        );

        outState.putLong(
                ESTADO_TIEMPO_RESTANTE,
                temporizadorControlador
                        .getTiempoRestante()
        );

        outState.putBoolean(
                ESTADO_EN_MARCHA,
                temporizadorControlador
                        .isTemporizadorEnMarcha()
        );
    }

    @Override
    protected void onDestroy() {
        cancelarCuentaAtras();
        super.onDestroy();
    }
}