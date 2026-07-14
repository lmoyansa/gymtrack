package com.example.gymtrack.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.repository.RutinaRepository;
import com.example.gymtrack.session.SessionManager;

public class CrearRutinaActivity extends Activity {

    private EditText etNombreRutina;
    private EditText etDescripcionRutina;
    private EditText etObjetivoRutina;

    private RutinaRepository rutinaRepository;
    private SessionManager sessionManager;

    private int idUsuarioActual;

    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_crear_rutina
        );

        rutinaRepository =
                RutinaRepository.getInstance();

        sessionManager =
                new SessionManager(this);

        idUsuarioActual =
                sessionManager.obtenerIdUsuario();

        if (idUsuarioActual < 0) {
            Toast.makeText(
                    this,
                    "No hay una sesión válida",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        etNombreRutina =
                findViewById(
                        R.id.etNombreRutina
                );

        etDescripcionRutina =
                findViewById(
                        R.id.etDescripcionRutina
                );

        etObjetivoRutina =
                findViewById(
                        R.id.etObjetivoRutina
                );

        Button btnGuardarRutina =
                findViewById(
                        R.id.btnGuardarRutina
                );

        Button btnCancelarCrearRutina =
                findViewById(
                        R.id.btnCancelarCrearRutina
                );

        btnGuardarRutina
                .setOnClickListener(
                        v -> guardarRutina()
                );

        btnCancelarCrearRutina
                .setOnClickListener(
                        v -> finish()
                );
    }

    private void guardarRutina() {
        String nombre =
                etNombreRutina
                        .getText()
                        .toString()
                        .trim();

        String descripcion =
                etDescripcionRutina
                        .getText()
                        .toString()
                        .trim();

        String objetivo =
                etObjetivoRutina
                        .getText()
                        .toString()
                        .trim();

        if (!RutinaRepository
                .validarNombreRutina(nombre)) {

            Toast.makeText(
                    this,
                    "El nombre de la rutina es obligatorio",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        rutinaRepository.crearRutina(
                idUsuarioActual,
                nombre,
                descripcion,
                objetivo
        );

        Toast.makeText(
                this,
                "Rutina creada correctamente",
                Toast.LENGTH_SHORT
        ).show();

        finish();
    }
}