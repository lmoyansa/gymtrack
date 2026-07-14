package com.example.gymtrack.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.gymtrack.R;
import com.example.gymtrack.model.Usuario;
import com.example.gymtrack.repository.UsuarioRepository;
import com.example.gymtrack.session.SessionManager;

public class MainActivity extends Activity {

    private Button btnMisRutinas;
    private Button btnIniciarEntrenamiento;
    private Button btnHistorial;
    private Button btnTemporizador;
    private Button btnCerrarSesion;

    private TextView tvUsuarioActual;

    private SessionManager sessionManager;
    private UsuarioRepository usuarioRepository;
    private Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager =
                new SessionManager(this);

        usuarioRepository =
                UsuarioRepository.getInstance();

        if (!cargarUsuarioActual()) {
            abrirLogin();
            return;
        }

        setContentView(R.layout.activity_main);

        tvUsuarioActual =
                findViewById(
                        R.id.tvUsuarioActual
                );

        btnMisRutinas =
                findViewById(
                        R.id.btnMisRutinas
                );

        btnIniciarEntrenamiento =
                findViewById(
                        R.id.btnIniciarEntrenamiento
                );

        btnHistorial =
                findViewById(
                        R.id.btnHistorial
                );

        btnTemporizador =
                findViewById(
                        R.id.btnTemporizador
                );

        btnCerrarSesion =
                findViewById(
                        R.id.btnCerrarSesion
                );

        mostrarUsuarioActual();

        btnMisRutinas.setOnClickListener(v ->
                abrirListadoRutinas()
        );

        btnIniciarEntrenamiento.setOnClickListener(v ->
                abrirListadoRutinas()
        );

        btnHistorial.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            this,
                            HistorialEntrenamientosActivity.class
                    );

            startActivity(intent);
        });

        btnTemporizador.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            this,
                            TemporizadorActivity.class
                    );

            startActivity(intent);
        });

        btnCerrarSesion.setOnClickListener(v ->
                mostrarConfirmacionCerrarSesion()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sessionManager == null) {
            return;
        }

        if (!cargarUsuarioActual()) {
            abrirLogin();
            return;
        }

        if (tvUsuarioActual != null) {
            mostrarUsuarioActual();
        }
    }

    private boolean cargarUsuarioActual() {
        if (!sessionManager.haySesionIniciada()) {
            usuarioActual = null;
            return false;
        }

        int idUsuario =
                sessionManager.obtenerIdUsuario();

        usuarioActual =
                usuarioRepository.obtenerUsuarioPorId(
                        idUsuario
                );

        if (usuarioActual == null) {
            sessionManager.cerrarSesion();
            return false;
        }

        return true;
    }

    private void mostrarUsuarioActual() {
        String nombre =
                usuarioActual.getNombre();

        tvUsuarioActual.setText(
                "Hola, " + nombre
        );
    }

    private void abrirListadoRutinas() {
        Intent intent =
                new Intent(
                        this,
                        RutinasActivity.class
                );

        startActivity(intent);
    }

    private void mostrarConfirmacionCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage(
                        "¿Seguro que quieres cerrar la sesión?"
                )
                .setPositiveButton(
                        "Cerrar sesión",
                        (dialog, which) ->
                                cerrarSesion()
                )
                .setNegativeButton(
                        "Cancelar",
                        null
                )
                .show();
    }

    private void cerrarSesion() {
        sessionManager.cerrarSesion();
        abrirLogin();
    }

    private void abrirLogin() {
        Intent intent =
                new Intent(
                        this,
                        LoginActivity.class
                );

        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);
        finish();
    }
}