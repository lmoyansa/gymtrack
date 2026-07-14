package com.example.gymtrack.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.model.Usuario;
import com.example.gymtrack.repository.UsuarioRepository;
import com.example.gymtrack.session.SessionManager;

public class RegistroActivity extends Activity {

    private static final int
            LONGITUD_MINIMA_PASSWORD = 8;

    private EditText etNombreRegistro;
    private EditText etEmailRegistro;
    private EditText etPasswordRegistro;
    private EditText etConfirmarPasswordRegistro;

    private UsuarioRepository usuarioRepository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_registro
        );

        usuarioRepository =
                UsuarioRepository.getInstance();

        sessionManager =
                new SessionManager(this);

        etNombreRegistro =
                findViewById(
                        R.id.etNombreRegistro
                );

        etEmailRegistro =
                findViewById(
                        R.id.etEmailRegistro
                );

        etPasswordRegistro =
                findViewById(
                        R.id.etPasswordRegistro
                );

        etConfirmarPasswordRegistro =
                findViewById(
                        R.id.etConfirmarPasswordRegistro
                );

        Button btnCrearCuenta =
                findViewById(R.id.btnCrearCuenta);

        Button btnVolverLogin =
                findViewById(R.id.btnVolverLogin);

        btnCrearCuenta.setOnClickListener(v ->
                crearCuenta()
        );

        btnVolverLogin.setOnClickListener(
                v -> finish()
        );
    }

    private void crearCuenta() {
        String nombre =
                etNombreRegistro
                        .getText()
                        .toString()
                        .trim();

        String email =
                etEmailRegistro
                        .getText()
                        .toString()
                        .trim();

        String password =
                etPasswordRegistro
                        .getText()
                        .toString();

        String confirmarPassword =
                etConfirmarPasswordRegistro
                        .getText()
                        .toString();

        if (nombre.isEmpty()) {
            etNombreRegistro.setError(
                    "Introduce tu nombre"
            );

            etNombreRegistro.requestFocus();
            return;
        }

        if (nombre.length() < 2) {
            etNombreRegistro.setError(
                    "El nombre debe tener al menos 2 caracteres"
            );

            etNombreRegistro.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmailRegistro.setError(
                    "Introduce tu correo electrónico"
            );

            etEmailRegistro.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            etEmailRegistro.setError(
                    "Introduce un correo electrónico válido"
            );

            etEmailRegistro.requestFocus();
            return;
        }

        if (usuarioRepository.existeEmail(email)) {
            etEmailRegistro.setError(
                    "Ya existe una cuenta con este correo"
            );

            etEmailRegistro.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPasswordRegistro.setError(
                    "Introduce una contraseña"
            );

            etPasswordRegistro.requestFocus();
            return;
        }

        if (password.length()
                < LONGITUD_MINIMA_PASSWORD) {

            etPasswordRegistro.setError(
                    "La contraseña debe tener al menos 8 caracteres"
            );

            etPasswordRegistro.requestFocus();
            return;
        }

        if (!password.equals(
                confirmarPassword
        )) {
            etConfirmarPasswordRegistro.setError(
                    "Las contraseñas no coinciden"
            );

            etConfirmarPasswordRegistro
                    .requestFocus();

            return;
        }

        Usuario usuario =
                usuarioRepository.registrarUsuario(
                        nombre,
                        email,
                        password
                );

        if (usuario == null) {
            Toast.makeText(
                    this,
                    "No se pudo crear la cuenta",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        sessionManager.iniciarSesion(
                usuario.getIdUsuario()
        );

        Toast.makeText(
                this,
                "Cuenta creada correctamente",
                Toast.LENGTH_SHORT
        ).show();

        abrirPantallaPrincipal();
    }

    private void abrirPantallaPrincipal() {
        Intent intent =
                new Intent(
                        this,
                        MainActivity.class
                );

        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);
        finish();
    }
}