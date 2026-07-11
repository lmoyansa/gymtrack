package com.example.gymtrack.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
            View tarjetaRutina = crearTarjetaRutina(rutina);
            contenedorRutinas.addView(tarjetaRutina);
        }
    }

    private View crearTarjetaRutina(Rutina rutina) {
        LinearLayout tarjeta = new LinearLayout(this);

        tarjeta.setOrientation(LinearLayout.VERTICAL);
        tarjeta.setGravity(Gravity.START);
        tarjeta.setPadding(
                dpToPx(20),
                dpToPx(18),
                dpToPx(20),
                dpToPx(18)
        );
        tarjeta.setBackgroundResource(R.drawable.gt_background_card);
        tarjeta.setClickable(true);
        tarjeta.setFocusable(true);

        LinearLayout.LayoutParams parametrosTarjeta =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosTarjeta.setMargins(
                0,
                0,
                0,
                dpToPx(16)
        );

        tarjeta.setLayoutParams(parametrosTarjeta);

        TextView tvNombre = new TextView(this);
        tvNombre.setText(rutina.getNombre());
        tvNombre.setTextColor(getColor(R.color.gt_foreground));
        tvNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tvNombre.setTypeface(
                Typeface.create("sans-serif-condensed", Typeface.BOLD)
        );
        tvNombre.setLetterSpacing(0.02f);

        tarjeta.addView(tvNombre);

        String objetivo = rutina.getObjetivo();

        if (objetivo != null && !objetivo.trim().isEmpty()) {
            TextView tvObjetivo = new TextView(this);
            tvObjetivo.setText(objetivo.toUpperCase());
            tvObjetivo.setTextColor(getColor(R.color.gt_primary));
            tvObjetivo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvObjetivo.setTypeface(Typeface.DEFAULT_BOLD);
            tvObjetivo.setLetterSpacing(0.06f);

            LinearLayout.LayoutParams parametrosObjetivo =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            parametrosObjetivo.setMargins(
                    0,
                    dpToPx(6),
                    0,
                    0
            );

            tvObjetivo.setLayoutParams(parametrosObjetivo);
            tarjeta.addView(tvObjetivo);
        }

        String descripcion = rutina.getDescripcion();

        if (descripcion != null && !descripcion.trim().isEmpty()) {
            TextView tvDescripcion = new TextView(this);
            tvDescripcion.setText(descripcion);
            tvDescripcion.setTextColor(getColor(R.color.gt_muted));
            tvDescripcion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

            LinearLayout.LayoutParams parametrosDescripcion =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            parametrosDescripcion.setMargins(
                    0,
                    dpToPx(12),
                    0,
                    0
            );

            tvDescripcion.setLayoutParams(parametrosDescripcion);
            tarjeta.addView(tvDescripcion);
        }

        TextView tvAccion = new TextView(this);
        tvAccion.setText("VER DETALLE  →");
        tvAccion.setTextColor(getColor(R.color.gt_foreground));
        tvAccion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tvAccion.setTypeface(Typeface.DEFAULT_BOLD);
        tvAccion.setLetterSpacing(0.04f);

        LinearLayout.LayoutParams parametrosAccion =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosAccion.setMargins(
                0,
                dpToPx(18),
                0,
                0
        );

        tvAccion.setLayoutParams(parametrosAccion);
        tarjeta.addView(tvAccion);

        final int idRutinaSeleccionada = rutina.getIdRutina();

        tarjeta.setOnClickListener(v -> {
            Intent intent = new Intent(
                    this,
                    DetalleRutinaActivity.class
            );

            intent.putExtra(
                    DetalleRutinaActivity.EXTRA_ID_RUTINA,
                    idRutinaSeleccionada
            );

            startActivity(intent);
        });

        return tarjeta;
    }

    private int dpToPx(int dp) {
        return Math.round(
                dp * getResources().getDisplayMetrics().density
        );
    }
}