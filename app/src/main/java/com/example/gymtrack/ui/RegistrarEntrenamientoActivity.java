package com.example.gymtrack.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.model.Ejercicio;
import com.example.gymtrack.model.Entrenamiento;
import com.example.gymtrack.model.Rutina;
import com.example.gymtrack.model.RutinaEjercicio;
import com.example.gymtrack.repository.EjercicioRepository;
import com.example.gymtrack.repository.EntrenamientoRepository;
import com.example.gymtrack.repository.RutinaEjercicioRepository;
import com.example.gymtrack.repository.RutinaRepository;
import com.example.gymtrack.repository.SerieEntrenamientoRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistrarEntrenamientoActivity extends Activity {

    public static final String EXTRA_ID_RUTINA = "idRutina";

    private TextView tvNombreRutinaEntrenamiento;
    private TextView tvResumenEntrenamiento;
    private LinearLayout contenedorRegistroEjercicios;
    private EditText etObservacionesEntrenamiento;

    private RutinaRepository rutinaRepository;
    private EjercicioRepository ejercicioRepository;
    private RutinaEjercicioRepository rutinaEjercicioRepository;
    private EntrenamientoRepository entrenamientoRepository;
    private SerieEntrenamientoRepository serieRepository;

    private final List<RegistroSerieView> registrosSeries =
            new ArrayList<>();

    private int idRutina;
    private long tiempoInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.activity_registrar_entrenamiento
        );

        tvNombreRutinaEntrenamiento =
                findViewById(
                        R.id.tvNombreRutinaEntrenamiento
                );

        tvResumenEntrenamiento =
                findViewById(
                        R.id.tvResumenEntrenamiento
                );

        contenedorRegistroEjercicios =
                findViewById(
                        R.id.contenedorRegistroEjercicios
                );

        etObservacionesEntrenamiento =
                findViewById(
                        R.id.etObservacionesEntrenamiento
                );

        Button btnFinalizarEntrenamiento =
                findViewById(
                        R.id.btnFinalizarEntrenamiento
                );

        Button btnCancelarEntrenamiento =
                findViewById(
                        R.id.btnCancelarEntrenamiento
                );

        rutinaRepository =
                RutinaRepository.getInstance();

        ejercicioRepository =
                EjercicioRepository.getInstance();

        rutinaEjercicioRepository =
                RutinaEjercicioRepository.getInstance();

        entrenamientoRepository =
                EntrenamientoRepository.getInstance();

        serieRepository =
                SerieEntrenamientoRepository.getInstance();

        idRutina = getIntent().getIntExtra(
                EXTRA_ID_RUTINA,
                -1
        );

        if (idRutina == -1) {
            mostrarErrorYFinalizar(
                    "No se ha recibido una rutina válida"
            );

            return;
        }

        tiempoInicio = System.currentTimeMillis();

        cargarEntrenamiento();

        btnFinalizarEntrenamiento.setOnClickListener(
                v -> guardarEntrenamiento()
        );

        btnCancelarEntrenamiento.setOnClickListener(
                v -> mostrarConfirmacionCancelar()
        );
    }

    private void cargarEntrenamiento() {
        Rutina rutina =
                rutinaRepository.obtenerRutinaPorId(idRutina);

        if (rutina == null) {
            mostrarErrorYFinalizar(
                    "No se ha encontrado la rutina"
            );

            return;
        }

        List<RutinaEjercicio> relaciones =
                rutinaEjercicioRepository
                        .obtenerPorRutina(idRutina);

        if (relaciones.isEmpty()) {
            mostrarErrorYFinalizar(
                    "La rutina no tiene ejercicios"
            );

            return;
        }

        tvNombreRutinaEntrenamiento.setText(
                rutina.getNombre().toUpperCase(
                        Locale.getDefault()
                )
        );

        tvResumenEntrenamiento.setText(
                relaciones.size()
                        + (relaciones.size() == 1
                        ? " ejercicio planificado"
                        : " ejercicios planificados")
        );

        registrosSeries.clear();
        contenedorRegistroEjercicios.removeAllViews();

        for (RutinaEjercicio relacion : relaciones) {
            Ejercicio ejercicio =
                    ejercicioRepository
                            .obtenerEjercicioPorId(
                                    relacion.getIdEjercicio()
                            );

            if (ejercicio != null) {
                crearTarjetaEjercicio(
                        ejercicio,
                        relacion
                );
            }
        }
    }

    private void crearTarjetaEjercicio(
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

        tarjeta.setLayoutParams(parametrosTarjeta);

        TextView tvOrden = new TextView(this);

        tvOrden.setText(
                String.format(
                        Locale.getDefault(),
                        "EJERCICIO %02d",
                        relacion.getOrden()
                )
        );

        tvOrden.setTextColor(
                getColor(R.color.gt_primary)
        );

        tvOrden.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                12
        );

        tvOrden.setTypeface(Typeface.DEFAULT_BOLD);
        tvOrden.setLetterSpacing(0.08f);

        tarjeta.addView(tvOrden);

        TextView tvNombre = new TextView(this);

        tvNombre.setText(ejercicio.getNombre());
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

        LinearLayout.LayoutParams parametrosNombre =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosNombre.setMargins(
                0,
                dpToPx(6),
                0,
                dpToPx(16)
        );

        tvNombre.setLayoutParams(parametrosNombre);
        tarjeta.addView(tvNombre);

        for (int numeroSerie = 1;
             numeroSerie <= relacion.getSeriesPlanificadas();
             numeroSerie++) {

            crearFilaSerie(
                    tarjeta,
                    relacion,
                    numeroSerie
            );
        }

        contenedorRegistroEjercicios.addView(tarjeta);
    }

    private void crearFilaSerie(
            LinearLayout tarjeta,
            RutinaEjercicio relacion,
            int numeroSerie
    ) {
        LinearLayout bloqueSerie =
                new LinearLayout(this);

        bloqueSerie.setOrientation(
                LinearLayout.VERTICAL
        );

        bloqueSerie.setBackgroundResource(
                R.drawable.gt_button_secondary
        );

        bloqueSerie.setPadding(
                dpToPx(14),
                dpToPx(12),
                dpToPx(14),
                dpToPx(12)
        );

        LinearLayout.LayoutParams parametrosBloque =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosBloque.setMargins(
                0,
                0,
                0,
                dpToPx(12)
        );

        bloqueSerie.setLayoutParams(parametrosBloque);

        TextView tvSerie = new TextView(this);

        tvSerie.setText("SERIE " + numeroSerie);
        tvSerie.setTextColor(
                getColor(R.color.gt_foreground)
        );

        tvSerie.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                14
        );

        tvSerie.setTypeface(Typeface.DEFAULT_BOLD);

        bloqueSerie.addView(tvSerie);

        LinearLayout filaCampos =
                new LinearLayout(this);

        filaCampos.setOrientation(
                LinearLayout.HORIZONTAL
        );

        LinearLayout.LayoutParams parametrosFila =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosFila.setMargins(
                0,
                dpToPx(10),
                0,
                0
        );

        filaCampos.setLayoutParams(parametrosFila);

        EditText etPeso = crearCampoNumerico(
                "Peso kg",
                formatearPeso(
                        relacion.getPesoObjetivo()
                ),
                true
        );

        EditText etRepeticiones = crearCampoNumerico(
                "Repeticiones",
                String.valueOf(
                        relacion.getRepeticionesPlanificadas()
                ),
                false
        );

        LinearLayout.LayoutParams parametrosPeso =
                new LinearLayout.LayoutParams(
                        0,
                        dpToPx(52),
                        1
                );

        parametrosPeso.setMarginEnd(dpToPx(8));
        etPeso.setLayoutParams(parametrosPeso);

        LinearLayout.LayoutParams parametrosRepeticiones =
                new LinearLayout.LayoutParams(
                        0,
                        dpToPx(52),
                        1
                );

        etRepeticiones.setLayoutParams(
                parametrosRepeticiones
        );

        filaCampos.addView(etPeso);
        filaCampos.addView(etRepeticiones);

        bloqueSerie.addView(filaCampos);

        CheckBox cbCompletada = new CheckBox(this);

        cbCompletada.setText("Serie completada");
        cbCompletada.setTextColor(
                getColor(R.color.gt_foreground)
        );

        cbCompletada.setButtonTintList(
                ColorStateList.valueOf(
                        getColor(R.color.gt_primary)
                )
        );

        LinearLayout.LayoutParams parametrosCheck =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosCheck.setMargins(
                0,
                dpToPx(8),
                0,
                0
        );

        cbCompletada.setLayoutParams(parametrosCheck);

        bloqueSerie.addView(cbCompletada);
        tarjeta.addView(bloqueSerie);

        registrosSeries.add(
                new RegistroSerieView(
                        relacion.getIdRutinaEjercicio(),
                        numeroSerie,
                        relacion.getPesoObjetivo(),
                        relacion.getRepeticionesPlanificadas(),
                        etPeso,
                        etRepeticiones,
                        cbCompletada
                )
        );
    }

    private EditText crearCampoNumerico(
            String hint,
            String valor,
            boolean decimal
    ) {
        EditText campo = new EditText(this);

        campo.setHint(hint);
        campo.setText(valor);

        campo.setTextColor(
                getColor(R.color.gt_foreground)
        );

        campo.setHintTextColor(
                getColor(R.color.gt_muted)
        );

        campo.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                15
        );

        campo.setSingleLine(true);

        campo.setBackgroundResource(
                R.drawable.gt_background_input
        );

        campo.setPadding(
                dpToPx(14),
                0,
                dpToPx(14),
                0
        );

        if (decimal) {
            campo.setInputType(
                    InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_FLAG_DECIMAL
            );
        } else {
            campo.setInputType(
                    InputType.TYPE_CLASS_NUMBER
            );
        }

        return campo;
    }

    private void guardarEntrenamiento() {
        List<SeriePendiente> seriesPendientes =
                new ArrayList<>();

        boolean algunaCompletada = false;

        for (RegistroSerieView registro : registrosSeries) {
            String textoPeso =
                    registro.etPeso
                            .getText()
                            .toString()
                            .trim();

            String textoRepeticiones =
                    registro.etRepeticiones
                            .getText()
                            .toString()
                            .trim();

            boolean completada =
                    registro.cbCompletada.isChecked();

            double peso = registro.pesoPlanificado;
            int repeticiones =
                    registro.repeticionesPlanificadas;

            try {
                if (!textoPeso.isEmpty()) {
                    peso = Double.parseDouble(textoPeso);
                }

                if (!textoRepeticiones.isEmpty()) {
                    repeticiones =
                            Integer.parseInt(
                                    textoRepeticiones
                            );
                }

            } catch (NumberFormatException exception) {
                Toast.makeText(
                        this,
                        "Introduce valores numéricos válidos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (completada
                    && (textoPeso.isEmpty()
                    || textoRepeticiones.isEmpty())) {

                Toast.makeText(
                        this,
                        "Completa el peso y las repeticiones de las series realizadas",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (peso < 0 || repeticiones <= 0) {
                Toast.makeText(
                        this,
                        "El peso no puede ser negativo y las repeticiones deben ser mayores que cero",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (completada) {
                algunaCompletada = true;
            }

            seriesPendientes.add(
                    new SeriePendiente(
                            registro.idRutinaEjercicio,
                            registro.numeroSerie,
                            peso,
                            repeticiones,
                            completada
                    )
            );
        }

        if (!algunaCompletada) {
            Toast.makeText(
                    this,
                    "Marca al menos una serie como completada",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        int duracionMinutos =
                (int) (
                        (System.currentTimeMillis()
                                - tiempoInicio)
                                / 60000L
                );

        String observaciones =
                etObservacionesEntrenamiento
                        .getText()
                        .toString()
                        .trim();

        Entrenamiento entrenamiento =
                entrenamientoRepository
                        .crearEntrenamiento(
                                1,
                                idRutina,
                                new Date(),
                                duracionMinutos,
                                observaciones
                        );

        for (SeriePendiente serie : seriesPendientes) {
            serieRepository.crearSerie(
                    entrenamiento.getIdEntrenamiento(),
                    serie.idRutinaEjercicio,
                    serie.numeroSerie,
                    serie.peso,
                    serie.repeticiones,
                    serie.completada
            );
        }

        Toast.makeText(
                this,
                "Entrenamiento guardado correctamente",
                Toast.LENGTH_SHORT
        ).show();

        setResult(RESULT_OK);
        finish();
    }

    private void mostrarConfirmacionCancelar() {
        new AlertDialog.Builder(this)
                .setTitle("Cancelar entrenamiento")
                .setMessage(
                        "Se perderán los datos introducidos. ¿Quieres salir?"
                )
                .setPositiveButton(
                        "Salir",
                        (dialog, which) -> finish()
                )
                .setNegativeButton("Continuar", null)
                .show();
    }

    private void mostrarErrorYFinalizar(
            String mensaje
    ) {
        Toast.makeText(
                this,
                mensaje,
                Toast.LENGTH_SHORT
        ).show();

        finish();
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

    private int dpToPx(int dp) {
        return Math.round(
                dp
                        * getResources()
                        .getDisplayMetrics()
                        .density
        );
    }

    private static class RegistroSerieView {

        private final int idRutinaEjercicio;
        private final int numeroSerie;
        private final double pesoPlanificado;
        private final int repeticionesPlanificadas;
        private final EditText etPeso;
        private final EditText etRepeticiones;
        private final CheckBox cbCompletada;

        private RegistroSerieView(
                int idRutinaEjercicio,
                int numeroSerie,
                double pesoPlanificado,
                int repeticionesPlanificadas,
                EditText etPeso,
                EditText etRepeticiones,
                CheckBox cbCompletada
        ) {
            this.idRutinaEjercicio =
                    idRutinaEjercicio;

            this.numeroSerie = numeroSerie;
            this.pesoPlanificado = pesoPlanificado;

            this.repeticionesPlanificadas =
                    repeticionesPlanificadas;

            this.etPeso = etPeso;
            this.etRepeticiones = etRepeticiones;
            this.cbCompletada = cbCompletada;
        }
    }

    private static class SeriePendiente {

        private final int idRutinaEjercicio;
        private final int numeroSerie;
        private final double peso;
        private final int repeticiones;
        private final boolean completada;

        private SeriePendiente(
                int idRutinaEjercicio,
                int numeroSerie,
                double peso,
                int repeticiones,
                boolean completada
        ) {
            this.idRutinaEjercicio =
                    idRutinaEjercicio;

            this.numeroSerie = numeroSerie;
            this.peso = peso;
            this.repeticiones = repeticiones;
            this.completada = completada;
        }
    }
}