package com.example.gymtrack.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.gymtrack.R;

public class MainActivity extends Activity {

    private Button btnMisRutinas;
    private Button btnIniciarEntrenamiento;
    private Button btnHistorial;
    private Button btnTemporizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnMisRutinas =
                findViewById(R.id.btnMisRutinas);

        btnIniciarEntrenamiento =
                findViewById(
                        R.id.btnIniciarEntrenamiento
                );

        btnHistorial =
                findViewById(R.id.btnHistorial);

        btnTemporizador =
                findViewById(R.id.btnTemporizador);

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

        btnTemporizador.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "Temporizador pendiente de implementar",
                        Toast.LENGTH_SHORT
                ).show()
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
}