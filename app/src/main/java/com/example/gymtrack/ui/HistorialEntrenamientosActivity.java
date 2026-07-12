package com.example.gymtrack.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gymtrack.R;
import com.example.gymtrack.model.Entrenamiento;
import com.example.gymtrack.model.Rutina;
import com.example.gymtrack.model.SerieEntrenamiento;
import com.example.gymtrack.repository.EntrenamientoRepository;
import com.example.gymtrack.repository.RutinaRepository;
import com.example.gymtrack.repository.SerieEntrenamientoRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistorialEntrenamientosActivity extends Activity {

    private TextView tvHistorialVacio;
    private LinearLayout contenedorHistorial;

    private EntrenamientoRepository entrenamientoRepository;
    private SerieEntrenamientoRepository serieRepository;
    private RutinaRepository rutinaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_historial_entrenamientos
        );

        tvHistorialVacio =
                findViewById(R.id.tvHistorialVacio);

        contenedorHistorial =
                findViewById(R.id.contenedorHistorial);

        Button btnVolverInicioHistorial =
                findViewById(
                        R.id.btnVolverInicioHistorial
                );

        entrenamientoRepository =
                EntrenamientoRepository.getInstance();

        serieRepository =
                SerieEntrenamientoRepository.getInstance();

        rutinaRepository =
                RutinaRepository.getInstance();

        btnVolverInicioHistorial.setOnClickListener(
                v -> finish()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarHistorial();
    }

    private void cargarHistorial() {
        contenedorHistorial.removeAllViews();

        List<Entrenamiento> entrenamientos =
                entrenamientoRepository
                        .obtenerEntrenamientos();

        if (entrenamientos.isEmpty()) {
            tvHistorialVacio.setVisibility(View.VISIBLE);
            return;
        }

        tvHistorialVacio.setVisibility(View.GONE);

        for (int i = entrenamientos.size() - 1;
             i >= 0;
             i--) {

            Entrenamiento entrenamiento =
                    entrenamientos.get(i);

            View tarjeta =
                    crearTarjetaEntrenamiento(
                            entrenamiento
                    );

            contenedorHistorial.addView(tarjeta);
        }
    }

    private View crearTarjetaEntrenamiento(
            Entrenamiento entrenamiento
    ) {
        LinearLayout tarjeta =
                new LinearLayout(this);

        tarjeta.setOrientation(LinearLayout.VERTICAL);

        tarjeta.setBackgroundResource(
                R.drawable.gt_background_card
        );

        tarjeta.setPadding(
                dpToPx(20),
                dpToPx(18),
                dpToPx(20),
                dpToPx(18)
        );

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

        TextView tvFecha = new TextView(this);

        tvFecha.setText(
                formatearFecha(entrenamiento.getFecha())
        );

        tvFecha.setTextColor(
                getColor(R.color.gt_primary)
        );

        tvFecha.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                12
        );

        tvFecha.setTypeface(Typeface.DEFAULT_BOLD);
        tvFecha.setLetterSpacing(0.06f);

        tarjeta.addView(tvFecha);

        TextView tvNombreRutina = new TextView(this);

        tvNombreRutina.setText(
                obtenerNombreRutina(
                        entrenamiento.getIdRutina()
                )
        );

        tvNombreRutina.setTextColor(
                getColor(R.color.gt_foreground)
        );

        tvNombreRutina.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                24
        );

        tvNombreRutina.setTypeface(
                Typeface.create(
                        "sans-serif-condensed",
                        Typeface.BOLD
                )
        );

        LinearLayout.LayoutParams parametrosNombre =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosNombre.setMargins(
                0,
                dpToPx(6),
                0,
                0
        );

        tvNombreRutina.setLayoutParams(parametrosNombre);

        tarjeta.addView(tvNombreRutina);

        LinearLayout panelResumen =
                new LinearLayout(this);

        panelResumen.setOrientation(
                LinearLayout.VERTICAL
        );

        panelResumen.setBackgroundResource(
                R.drawable.gt_button_secondary
        );

        panelResumen.setPadding(
                dpToPx(16),
                dpToPx(14),
                dpToPx(16),
                dpToPx(14)
        );

        LinearLayout.LayoutParams parametrosResumen =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosResumen.setMargins(
                0,
                dpToPx(16),
                0,
                0
        );

        panelResumen.setLayoutParams(parametrosResumen);

        TextView tvDuracion = new TextView(this);

        tvDuracion.setText(
                "Duración: "
                        + formatearDuracion(
                        entrenamiento
                                .getDuracionMinutos()
                )
        );

        tvDuracion.setTextColor(
                getColor(R.color.gt_foreground)
        );

        tvDuracion.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                15
        );

        panelResumen.addView(tvDuracion);

        TextView tvSeries = new TextView(this);

        tvSeries.setText(
                obtenerResumenSeries(
                        entrenamiento.getIdEntrenamiento()
                )
        );

        tvSeries.setTextColor(
                getColor(R.color.gt_foreground)
        );

        tvSeries.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                15
        );

        LinearLayout.LayoutParams parametrosSeries =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosSeries.setMargins(
                0,
                dpToPx(6),
                0,
                0
        );

        tvSeries.setLayoutParams(parametrosSeries);

        panelResumen.addView(tvSeries);
        tarjeta.addView(panelResumen);

        String observaciones =
                entrenamiento.getObservaciones();

        if (observaciones != null
                && !observaciones.trim().isEmpty()) {

            TextView tvObservaciones =
                    new TextView(this);

            tvObservaciones.setText(
                    "“" + observaciones + "”"
            );

            tvObservaciones.setTextColor(
                    getColor(R.color.gt_muted)
            );

            tvObservaciones.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP,
                    14
            );

            LinearLayout.LayoutParams
                    parametrosObservaciones =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            parametrosObservaciones.setMargins(
                    0,
                    dpToPx(14),
                    0,
                    0
            );

            tvObservaciones.setLayoutParams(
                    parametrosObservaciones
            );

            tarjeta.addView(tvObservaciones);
        }

        return tarjeta;
    }

    private String obtenerNombreRutina(int idRutina) {
        Rutina rutina =
                rutinaRepository.obtenerRutinaPorId(
                        idRutina
                );

        if (rutina == null) {
            return "Rutina eliminada";
        }

        return rutina.getNombre();
    }

    private String obtenerResumenSeries(
            int idEntrenamiento
    ) {
        List<SerieEntrenamiento> series =
                serieRepository.obtenerPorEntrenamiento(
                        idEntrenamiento
                );

        int seriesCompletadas = 0;

        for (SerieEntrenamiento serie : series) {
            if (serie.isCompletada()) {
                seriesCompletadas++;
            }
        }

        return seriesCompletadas
                + " de "
                + series.size()
                + " series completadas";
    }

    private String formatearFecha(Date fecha) {
        if (fecha == null) {
            return "FECHA NO DISPONIBLE";
        }

        SimpleDateFormat formato =
                new SimpleDateFormat(
                        "dd/MM/yyyy · HH:mm",
                        Locale.getDefault()
                );

        return formato
                .format(fecha)
                .toUpperCase(Locale.getDefault());
    }

    private String formatearDuracion(
            int duracionMinutos
    ) {
        if (duracionMinutos <= 0) {
            return "menos de 1 minuto";
        }

        if (duracionMinutos == 1) {
            return "1 minuto";
        }

        return duracionMinutos + " minutos";
    }

    private int dpToPx(int dp) {
        return Math.round(
                dp
                        * getResources()
                        .getDisplayMetrics()
                        .density
        );
    }
}