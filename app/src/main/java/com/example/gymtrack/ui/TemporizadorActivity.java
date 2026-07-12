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

import java.util.Locale;

public class TemporizadorActivity extends Activity {

    private static final long TIEMPO_60_SEGUNDOS =
            60_000L;

    private static final long TIEMPO_90_SEGUNDOS =
            90_000L;

    private static final long TIEMPO_120_SEGUNDOS =
            120_000L;

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

    private long tiempoSeleccionado =
            TIEMPO_90_SEGUNDOS;

    private long tiempoRestante =
            TIEMPO_90_SEGUNDOS;

    private boolean temporizadorEnMarcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                findViewById(R.id.btnTiempo60);

        btnTiempo90 =
                findViewById(R.id.btnTiempo90);

        btnTiempo120 =
                findViewById(R.id.btnTiempo120);

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
            tiempoSeleccionado =
                    savedInstanceState.getLong(
                            ESTADO_TIEMPO_SELECCIONADO,
                            TIEMPO_90_SEGUNDOS
                    );

            tiempoRestante =
                    savedInstanceState.getLong(
                            ESTADO_TIEMPO_RESTANTE,
                            tiempoSeleccionado
                    );

            reanudarTemporizador =
                    savedInstanceState.getBoolean(
                            ESTADO_EN_MARCHA,
                            false
                    );
        }

        btnTiempo60.setOnClickListener(v ->
                seleccionarTiempo(
                        TIEMPO_60_SEGUNDOS
                )
        );

        btnTiempo90.setOnClickListener(v ->
                seleccionarTiempo(
                        TIEMPO_90_SEGUNDOS
                )
        );

        btnTiempo120.setOnClickListener(v ->
                seleccionarTiempo(
                        TIEMPO_120_SEGUNDOS
                )
        );

        btnIniciarPausarTemporizador
                .setOnClickListener(v -> {

                    if (temporizadorEnMarcha) {
                        pausarTemporizador();
                    } else {
                        iniciarTemporizador();
                    }
                });

        btnReiniciarTemporizador
                .setOnClickListener(v ->
                        reiniciarTemporizador()
                );

        btnVolverInicioTemporizador
                .setOnClickListener(v -> finish());

        actualizarTiempoMostrado();
        actualizarBotonesRapidos();

        if (reanudarTemporizador
                && tiempoRestante > 0) {

            iniciarTemporizador();

        } else {
            actualizarEstadoInicial();
        }
    }

    private void seleccionarTiempo(long tiempo) {
        cancelarCuentaAtras();

        tiempoSeleccionado = tiempo;
        tiempoRestante = tiempo;
        temporizadorEnMarcha = false;

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
        if (tiempoRestante <= 0) {
            tiempoRestante = tiempoSeleccionado;
        }

        temporizadorEnMarcha = true;

        tvEstadoTemporizador.setText(
                "DESCANSANDO"
        );

        btnIniciarPausarTemporizador.setText(
                "Pausar"
        );

        countDownTimer =
                new CountDownTimer(
                        tiempoRestante,
                        1_000L
                ) {
                    @Override
                    public void onTick(
                            long millisHastaFinalizar
                    ) {
                        tiempoRestante =
                                millisHastaFinalizar;

                        actualizarTiempoMostrado();
                    }

                    @Override
                    public void onFinish() {
                        tiempoRestante = 0;
                        temporizadorEnMarcha = false;

                        actualizarTiempoMostrado();

                        tvEstadoTemporizador.setText(
                                "DESCANSO TERMINADO"
                        );

                        btnIniciarPausarTemporizador
                                .setText("Repetir");

                        avisarFinTemporizador();
                    }
                }.start();
    }

    private void pausarTemporizador() {
        cancelarCuentaAtras();

        temporizadorEnMarcha = false;

        tvEstadoTemporizador.setText(
                "TEMPORIZADOR PAUSADO"
        );

        btnIniciarPausarTemporizador.setText(
                "Continuar"
        );
    }

    private void reiniciarTemporizador() {
        cancelarCuentaAtras();

        tiempoRestante = tiempoSeleccionado;
        temporizadorEnMarcha = false;

        tvEstadoTemporizador.setText(
                "LISTO PARA EMPEZAR"
        );

        btnIniciarPausarTemporizador.setText(
                "Iniciar"
        );

        actualizarTiempoMostrado();
    }

    private void actualizarEstadoInicial() {
        temporizadorEnMarcha = false;

        if (tiempoRestante <= 0) {
            tiempoRestante = tiempoSeleccionado;
        }

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
                (tiempoRestante + 999L) / 1_000L;

        long minutos = segundosTotales / 60L;
        long segundos = segundosTotales % 60L;

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
        btnTiempo60.setAlpha(
                tiempoSeleccionado
                        == TIEMPO_60_SEGUNDOS
                        ? 1.0f
                        : 0.6f
        );

        btnTiempo90.setAlpha(
                tiempoSeleccionado
                        == TIEMPO_90_SEGUNDOS
                        ? 1.0f
                        : 0.6f
        );

        btnTiempo120.setAlpha(
                tiempoSeleccionado
                        == TIEMPO_120_SEGUNDOS
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
                tiempoSeleccionado
        );

        outState.putLong(
                ESTADO_TIEMPO_RESTANTE,
                tiempoRestante
        );

        outState.putBoolean(
                ESTADO_EN_MARCHA,
                temporizadorEnMarcha
        );
    }

    @Override
    protected void onDestroy() {
        cancelarCuentaAtras();
        super.onDestroy();
    }
}