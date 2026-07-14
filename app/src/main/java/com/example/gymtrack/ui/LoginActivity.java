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

public class LoginActivity extends Activity {

    private EditText etEmailLogin;
    private EditText etPasswordLogin;

    private UsuarioRepository usuarioRepository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuarioRepository =
                UsuarioRepository.getInstance();

        sessionManager =
                new SessionManager(this);

        if (sesionValidaExistente()) {
            abrirPantallaPrincipal();
            return;
        }

        setContentView(R.layout.activity_login);

        etEmailLogin =
                findViewById(R.id.etEmailLogin);

        etPasswordLogin =
                findViewById(R.id.etPasswordLogin);

        Button btnIniciarSesion =
                findViewById(R.id.btnIniciarSesion);

        Button btnIrRegistro =
                findViewById(R.id.btnIrRegistro);

        btnIniciarSesion.setOnClickListener(v ->
                iniciarSesion()
        );

        btnIrRegistro.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            LoginActivity.this,
                            RegistroActivity.class
                    );

            startActivity(intent);
        });
    }

    private boolean sesionValidaExistente() {
        if (!sessionManager.haySesionIniciada()) {
            return false;
        }

        int idUsuario =
                sessionManager.obtenerIdUsuario();

        Usuario usuario =
                usuarioRepository.obtenerUsuarioPorId(
                        idUsuario
                );

        if (usuario == null) {
            sessionManager.cerrarSesion();
            return false;
        }

        return true;
    }

    private void iniciarSesion() {
        String email =
                etEmailLogin
                        .getText()
                        .toString()
                        .trim();

        String password =
                etPasswordLogin
                        .getText()
                        .toString();

        if (email.isEmpty()) {
            etEmailLogin.setError(
                    "Introduce tu correo electrónico"
            );

            etEmailLogin.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            etEmailLogin.setError(
                    "Introduce un correo electrónico válido"
            );

            etEmailLogin.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPasswordLogin.setError(
                    "Introduce tu contraseña"
            );

            etPasswordLogin.requestFocus();
            return;
        }

        Usuario usuario =
                usuarioRepository.autenticar(
                        email,
                        password
                );

        if (usuario == null) {
            Toast.makeText(
                    this,
                    "Correo o contraseña incorrectos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        sessionManager.iniciarSesion(
                usuario.getIdUsuario()
        );

        Toast.makeText(
                this,
                "Sesión iniciada correctamente",
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