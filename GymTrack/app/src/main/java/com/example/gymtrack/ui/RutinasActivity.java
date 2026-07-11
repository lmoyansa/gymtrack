package com.example.gymtrack.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.model.Rutina;
import com.example.gymtrack.repository.RutinaRepository;

import java.util.List;

public class RutinasActivity extends Activity {

    private Button btnCrearRutina;
    private Button btnVolverHome;
    private TextView tvMensajeVacio;
    private LinearLayout contenedorRutinas;

    private RutinaRepository rutinaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas);

        btnCrearRutina = findViewById(R.id.btnCrearRutina);
        btnVolverHome = findViewById(R.id.btnVolverHome);
        tvMensajeVacio = findViewById(R.id.tvMensajeVacio);
        contenedorRutinas = findViewById(R.id.contenedorRutinas);

        rutinaRepository = RutinaRepository.getInstance();

        cargarRutinas();

        btnCrearRutina.setOnClickListener(v -> {
            Intent intent = new Intent(this, CrearRutinaActivity.class);
            startActivity(intent);
        });

        btnVolverHome.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarRutinas();
    }

    private void cargarRutinas() {
        contenedorRutinas.removeAllViews();

        List<Rutina> rutinas = rutinaRepository.obtenerRutinas();

        if (rutinas.isEmpty()) {
            tvMensajeVacio.setVisibility(View.VISIBLE);
            return;
        }

        tvMensajeVacio.setVisibility(View.GONE);

        for (Rutina rutina : rutinas) {
            TextView tvRutina = new TextView(this);

            String textoRutina = rutina.getNombre();

            if (rutina.getObjetivo() != null && !rutina.getObjetivo().trim().isEmpty()) {
                textoRutina += "\nObjetivo: " + rutina.getObjetivo();
            }

            if (rutina.getDescripcion() != null && !rutina.getDescripcion().trim().isEmpty()) {
                textoRutina += "\n" + rutina.getDescripcion();
            }

            tvRutina.setText(textoRutina);
            tvRutina.setTextSize(16);
            tvRutina.setTextColor(getResources().getColor(android.R.color.black));
            tvRutina.setPadding(24, 24, 24, 24);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16);
            tvRutina.setLayoutParams(params);

            tvRutina.setBackgroundColor(getResources().getColor(android.R.color.white));

            final int idRutinaSeleccionada = rutina.getIdRutina();

            tvRutina.setOnClickListener(v -> {
                Intent intent = new Intent(this, DetalleRutinaActivity.class);
                intent.putExtra(DetalleRutinaActivity.EXTRA_ID_RUTINA, idRutinaSeleccionada);
                startActivity(intent);
            });

            contenedorRutinas.addView(tvRutina);
        }
    }
}