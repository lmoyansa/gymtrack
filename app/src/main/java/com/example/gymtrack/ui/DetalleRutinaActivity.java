package com.example.gymtrack.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.model.Ejercicio;
import com.example.gymtrack.model.Rutina;
import com.example.gymtrack.model.RutinaEjercicio;
import com.example.gymtrack.repository.EjercicioRepository;
import com.example.gymtrack.repository.RutinaEjercicioRepository;
import com.example.gymtrack.repository.RutinaRepository;

import java.util.List;
import java.util.Locale;

public class DetalleRutinaActivity extends Activity {

    public static final String EXTRA_ID_RUTINA = "idRutina";

    private TextView tvNombreRutinaDetalle;
    private TextView tvObjetivoRutinaDetalle;
    private TextView tvDescripcionRutinaDetalle;
    private TextView tvMensajeSinEjercicios;

    private LinearLayout contenedorEjercicios;

    private Button btnEditarRutina;
    private Button btnEliminarRutina;
    private Button btnAnadirEjercicio;
    private Button btnEmpezarEntrenamiento;
    private Button btnVolverListadoRutinas;

    private RutinaRepository rutinaRepository;
    private EjercicioRepository ejercicioRepository;

    private RutinaEjercicioRepository
            rutinaEjercicioRepository;

    private Rutina rutinaActual;
    private int idRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_detalle_rutina
        );

        tvNombreRutinaDetalle =
                findViewById(
                        R.id.tvNombreRutinaDetalle
                );

        tvObjetivoRutinaDetalle =
                findViewById(
                        R.id.tvObjetivoRutinaDetalle
                );

        tvDescripcionRutinaDetalle =
                findViewById(
                        R.id.tvDescripcionRutinaDetalle
                );

        tvMensajeSinEjercicios =
                findViewById(
                        R.id.tvMensajeSinEjercicios
                );

        contenedorEjercicios =
                findViewById(
                        R.id.contenedorEjercicios
                );

        btnEditarRutina =
                findViewById(R.id.btnEditarRutina);

        btnEliminarRutina =
                findViewById(R.id.btnEliminarRutina);

        btnAnadirEjercicio =
                findViewById(R.id.btnAnadirEjercicio);

        btnEmpezarEntrenamiento =
                findViewById(
                        R.id.btnEmpezarEntrenamiento
                );

        btnVolverListadoRutinas =
                findViewById(
                        R.id.btnVolverListadoRutinas
                );

        rutinaRepository =
                RutinaRepository.getInstance();

        ejercicioRepository =
                EjercicioRepository.getInstance();

        rutinaEjercicioRepository =
                RutinaEjercicioRepository.getInstance();

        idRutina = getIntent().getIntExtra(
                EXTRA_ID_RUTINA,
                -1
        );

        if (idRutina == -1) {
            Toast.makeText(
                    this,
                    "No se ha recibido una rutina válida",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        btnEditarRutina.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            this,
                            EditarRutinaActivity.class
                    );

            intent.putExtra(
                    EditarRutinaActivity.EXTRA_ID_RUTINA,
                    idRutina
            );

            startActivity(intent);
        });

        btnEliminarRutina.setOnClickListener(v ->
                mostrarConfirmacionEliminarRutina()
        );

        btnAnadirEjercicio.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            this,
                            AnadirEjercicioActivity.class
                    );

            intent.putExtra(
                    AnadirEjercicioActivity.EXTRA_ID_RUTINA,
                    idRutina
            );

            startActivity(intent);
        });

        btnEmpezarEntrenamiento.setOnClickListener(v ->
                empezarEntrenamiento()
        );

        btnVolverListadoRutinas.setOnClickListener(
                v -> finish()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (rutinaRepository != null
                && idRutina != -1) {

            cargarRutina();
            cargarEjercicios();
        }
    }

    private void cargarRutina() {
        rutinaActual =
                rutinaRepository
                        .obtenerRutinaPorId(idRutina);

        if (rutinaActual == null) {
            Toast.makeText(
                    this,
                    "No se ha encontrado la rutina",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        mostrarDatosRutina();
    }

    private void mostrarDatosRutina() {
        tvNombreRutinaDetalle.setText(
                rutinaActual
                        .getNombre()
                        .toUpperCase(
                                Locale.getDefault()
                        )
        );

        String objetivo =
                rutinaActual.getObjetivo();

        if (objetivo == null
                || objetivo.trim().isEmpty()) {

            tvObjetivoRutinaDetalle.setText(
                    "OBJETIVO NO ESPECIFICADO"
            );

        } else {
            tvObjetivoRutinaDetalle.setText(
                    objetivo.toUpperCase(
                            Locale.getDefault()
                    )
            );
        }

        String descripcion =
                rutinaActual.getDescripcion();

        if (descripcion == null
                || descripcion.trim().isEmpty()) {

            tvDescripcionRutinaDetalle.setText(
                    "No se ha añadido una descripción para esta rutina."
            );

        } else {
            tvDescripcionRutinaDetalle.setText(
                    descripcion
            );
        }
    }

    private void cargarEjercicios() {
        contenedorEjercicios.removeAllViews();

        List<RutinaEjercicio> relaciones =
                rutinaEjercicioRepository
                        .obtenerPorRutina(idRutina);

        if (relaciones.isEmpty()) {
            tvMensajeSinEjercicios.setVisibility(
                    View.VISIBLE
            );

            return;
        }

        tvMensajeSinEjercicios.setVisibility(
                View.GONE
        );

        for (RutinaEjercicio relacion : relaciones) {
            Ejercicio ejercicio =
                    ejercicioRepository
                            .obtenerEjercicioPorId(
                                    relacion.getIdEjercicio()
                            );

            if (ejercicio == null) {
                continue;
            }

            View tarjetaEjercicio =
                    crearTarjetaEjercicio(
                            ejercicio,
                            relacion
                    );

            contenedorEjercicios.addView(
                    tarjetaEjercicio
            );
        }
    }

    private void empezarEntrenamiento() {
        List<RutinaEjercicio> relaciones =
                rutinaEjercicioRepository
                        .obtenerPorRutina(idRutina);

        if (relaciones.isEmpty()) {
            Toast.makeText(
                    this,
                    "Añade al menos un ejercicio antes de empezar",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Intent intent =
                new Intent(
                        this,
                        RegistrarEntrenamientoActivity.class
                );

        intent.putExtra(
                RegistrarEntrenamientoActivity.EXTRA_ID_RUTINA,
                idRutina
        );

        startActivity(intent);
    }

    private View crearTarjetaEjercicio(
            Ejercicio ejercicio,
            RutinaEjercicio relacion
    ) {
        LinearLayout tarjeta =
                new LinearLayout(this);

        tarjeta.setOrientation(
                LinearLayout.VERTICAL
        );

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

        tarjeta.setLayoutParams(
                parametrosTarjeta
        );

        TextView tvOrden =
                new TextView(this);

        String textoOrden =
                String.format(
                        Locale.getDefault(),
                        "EJERCICIO %02d",
                        relacion.getOrden()
                );

        tvOrden.setText(textoOrden);

        tvOrden.setTextColor(
                getColor(R.color.gt_primary)
        );

        tvOrden.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                12
        );

        tvOrden.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        tvOrden.setLetterSpacing(0.08f);

        tarjeta.addView(tvOrden);

        TextView tvNombre =
                new TextView(this);

        tvNombre.setText(
                ejercicio.getNombre()
        );

        tvNombre.setTextColor(
                getColor(R.color.gt_foreground)
        );

        tvNombre.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                22
        );

        tvNombre.setTypeface(
                Typeface.create(
                        "sans-serif-condensed",
                        Typeface.BOLD
                )
        );

        tvNombre.setLetterSpacing(0.02f);

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

        tvNombre.setLayoutParams(
                parametrosNombre
        );

        tarjeta.addView(tvNombre);

        String grupoMuscular =
                ejercicio.getGrupoMuscular();

        if (grupoMuscular == null
                || grupoMuscular.trim().isEmpty()) {

            grupoMuscular =
                    "Grupo muscular no especificado";
        }

        TextView tvGrupoMuscular =
                new TextView(this);

        tvGrupoMuscular.setText(
                grupoMuscular
        );

        tvGrupoMuscular.setTextColor(
                getColor(R.color.gt_muted)
        );

        tvGrupoMuscular.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                14
        );

        LinearLayout.LayoutParams parametrosGrupo =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosGrupo.setMargins(
                0,
                dpToPx(6),
                0,
                0
        );

        tvGrupoMuscular.setLayoutParams(
                parametrosGrupo
        );

        tarjeta.addView(tvGrupoMuscular);

        LinearLayout panelPlanificacion =
                new LinearLayout(this);

        panelPlanificacion.setOrientation(
                LinearLayout.VERTICAL
        );

        panelPlanificacion.setBackgroundResource(
                R.drawable.gt_button_secondary
        );

        panelPlanificacion.setPadding(
                dpToPx(16),
                dpToPx(14),
                dpToPx(16),
                dpToPx(14)
        );

        LinearLayout.LayoutParams parametrosPanel =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosPanel.setMargins(
                0,
                dpToPx(16),
                0,
                0
        );

        panelPlanificacion.setLayoutParams(
                parametrosPanel
        );

        TextView tvPlanificacion =
                new TextView(this);

        String textoPlanificacion =
                relacion.getSeriesPlanificadas()
                        + " series  ×  "
                        + relacion.getRepeticionesPlanificadas()
                        + " repeticiones"
                        + "\n"
                        + formatearPeso(
                        relacion.getPesoObjetivo()
                )
                        + " kg objetivo  ·  "
                        + relacion.getDescansoSegundos()
                        + " s descanso";

        tvPlanificacion.setText(
                textoPlanificacion
        );

        tvPlanificacion.setTextColor(
                getColor(R.color.gt_foreground)
        );

        tvPlanificacion.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                15
        );

        tvPlanificacion.setLineSpacing(
                dpToPx(4),
                1.0f
        );

        panelPlanificacion.addView(
                tvPlanificacion
        );

        tarjeta.addView(
                panelPlanificacion
        );

        String descripcion =
                ejercicio.getDescripcion();

        if (descripcion != null
                && !descripcion.trim().isEmpty()) {

            TextView tvDescripcion =
                    new TextView(this);

            tvDescripcion.setText(
                    descripcion
            );

            tvDescripcion.setTextColor(
                    getColor(R.color.gt_muted)
            );

            tvDescripcion.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP,
                    14
            );

            LinearLayout.LayoutParams
                    parametrosDescripcion =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            parametrosDescripcion.setMargins(
                    0,
                    dpToPx(14),
                    0,
                    0
            );

            tvDescripcion.setLayoutParams(
                    parametrosDescripcion
            );

            tarjeta.addView(tvDescripcion);
        }

        Button btnEditarEjercicio =
                new Button(this);

        btnEditarEjercicio.setText(
                "Editar ejercicio"
        );

        btnEditarEjercicio.setTextColor(
                getColor(R.color.gt_foreground)
        );

        btnEditarEjercicio.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                14
        );

        btnEditarEjercicio.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        btnEditarEjercicio.setBackgroundResource(
                R.drawable.gt_button_secondary
        );

        btnEditarEjercicio.setStateListAnimator(null);
        btnEditarEjercicio.setMinHeight(dpToPx(52));

        LinearLayout.LayoutParams parametrosEditar =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dpToPx(52)
                );

        parametrosEditar.setMargins(
                0,
                dpToPx(16),
                0,
                0
        );

        btnEditarEjercicio.setLayoutParams(
                parametrosEditar
        );

        btnEditarEjercicio.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            this,
                            EditarEjercicioActivity.class
                    );

            intent.putExtra(
                    EditarEjercicioActivity
                            .EXTRA_ID_EJERCICIO,
                    ejercicio.getIdEjercicio()
            );

            intent.putExtra(
                    EditarEjercicioActivity
                            .EXTRA_ID_RUTINA_EJERCICIO,
                    relacion.getIdRutinaEjercicio()
            );

            startActivity(intent);
        });

        tarjeta.addView(btnEditarEjercicio);

        Button btnEliminarEjercicio =
                new Button(this);

        btnEliminarEjercicio.setText(
                "Eliminar ejercicio"
        );

        btnEliminarEjercicio.setTextColor(
                getColor(R.color.gt_destructive)
        );

        btnEliminarEjercicio.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                14
        );

        btnEliminarEjercicio.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        btnEliminarEjercicio.setBackgroundResource(
                R.drawable.gt_button_danger
        );

        btnEliminarEjercicio.setStateListAnimator(null);
        btnEliminarEjercicio.setMinHeight(dpToPx(52));

        LinearLayout.LayoutParams parametrosEliminar =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dpToPx(52)
                );

        parametrosEliminar.setMargins(
                0,
                dpToPx(12),
                0,
                0
        );

        btnEliminarEjercicio.setLayoutParams(
                parametrosEliminar
        );

        btnEliminarEjercicio.setOnClickListener(v ->
                mostrarConfirmacionEliminarEjercicio(
                        ejercicio,
                        relacion
                )
        );

        tarjeta.addView(btnEliminarEjercicio);

        return tarjeta;
    }

    private String formatearPeso(double peso) {
        if (peso == Math.floor(peso)) {
            return String.valueOf((int) peso);
        }

        return String.format(
                Locale.getDefault(),
                "%.1f",
                peso
        );
    }

    private void mostrarConfirmacionEliminarEjercicio(
            Ejercicio ejercicio,
            RutinaEjercicio relacion
    ) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar ejercicio")
                .setMessage(
                        "¿Seguro que quieres eliminar \""
                                + ejercicio.getNombre()
                                + "\" de esta rutina?"
                )
                .setPositiveButton(
                        "Eliminar",
                        (dialog, which) ->
                                eliminarEjercicio(
                                        ejercicio,
                                        relacion
                                )
                )
                .setNegativeButton(
                        "Cancelar",
                        null
                )
                .show();
    }

    private void eliminarEjercicio(
            Ejercicio ejercicio,
            RutinaEjercicio relacion
    ) {
        boolean relacionEliminada =
                rutinaEjercicioRepository
                        .eliminarRutinaEjercicio(
                                relacion
                                        .getIdRutinaEjercicio()
                        );

        if (!relacionEliminada) {
            Toast.makeText(
                    this,
                    "No se pudo eliminar el ejercicio",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        ejercicioRepository.eliminarEjercicio(
                ejercicio.getIdEjercicio()
        );

        cargarEjercicios();

        Toast.makeText(
                this,
                "Ejercicio eliminado correctamente",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void mostrarConfirmacionEliminarRutina() {
        if (rutinaActual == null) {
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Eliminar rutina")
                .setMessage(
                        "¿Seguro que quieres eliminar la rutina \""
                                + rutinaActual.getNombre()
                                + "\"?"
                )
                .setPositiveButton(
                        "Eliminar",
                        (dialog, which) ->
                                eliminarRutina()
                )
                .setNegativeButton(
                        "Cancelar",
                        null
                )
                .show();
    }

    private void eliminarRutina() {
        boolean eliminada =
                rutinaRepository
                        .eliminarRutina(idRutina);

        if (eliminada) {
            Toast.makeText(
                    this,
                    "Rutina eliminada correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {
            Toast.makeText(
                    this,
                    "No se pudo eliminar la rutina",
                    Toast.LENGTH_SHORT
            ).show();
        }
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