package com.example.gymtrack.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.model.Ejercicio;
import com.example.gymtrack.model.RutinaEjercicio;
import com.example.gymtrack.repository.EjercicioRepository;
import com.example.gymtrack.repository.RutinaEjercicioRepository;

public class EditarEjercicioActivity extends Activity {

    public static final String EXTRA_ID_EJERCICIO =
            "idEjercicio";

    public static final String EXTRA_ID_RUTINA_EJERCICIO =
            "idRutinaEjercicio";

    private EditText etNombreEjercicioEditar;
    private EditText etGrupoMuscularEditar;
    private EditText etDescripcionEjercicioEditar;
    private EditText etSeriesPlanificadasEditar;
    private EditText etRepeticionesPlanificadasEditar;
    private EditText etPesoObjetivoEditar;
    private EditText etDescansoSegundosEditar;

    private EjercicioRepository ejercicioRepository;

    private RutinaEjercicioRepository
            rutinaEjercicioRepository;

    private Ejercicio ejercicioActual;
    private RutinaEjercicio rutinaEjercicioActual;

    private int idEjercicio;
    private int idRutinaEjercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_editar_ejercicio
        );

        etNombreEjercicioEditar =
                findViewById(
                        R.id.etNombreEjercicioEditar
                );

        etGrupoMuscularEditar =
                findViewById(
                        R.id.etGrupoMuscularEditar
                );

        etDescripcionEjercicioEditar =
                findViewById(
                        R.id.etDescripcionEjercicioEditar
                );

        etSeriesPlanificadasEditar =
                findViewById(
                        R.id.etSeriesPlanificadasEditar
                );

        etRepeticionesPlanificadasEditar =
                findViewById(
                        R.id.etRepeticionesPlanificadasEditar
                );

        etPesoObjetivoEditar =
                findViewById(
                        R.id.etPesoObjetivoEditar
                );

        etDescansoSegundosEditar =
                findViewById(
                        R.id.etDescansoSegundosEditar
                );

        Button btnGuardarCambiosEjercicio =
                findViewById(
                        R.id.btnGuardarCambiosEjercicio
                );

        Button btnCancelarEditarEjercicio =
                findViewById(
                        R.id.btnCancelarEditarEjercicio
                );

        ejercicioRepository =
                EjercicioRepository.getInstance();

        rutinaEjercicioRepository =
                RutinaEjercicioRepository.getInstance();

        idEjercicio = getIntent().getIntExtra(
                EXTRA_ID_EJERCICIO,
                -1
        );

        idRutinaEjercicio = getIntent().getIntExtra(
                EXTRA_ID_RUTINA_EJERCICIO,
                -1
        );

        if (idEjercicio == -1
                || idRutinaEjercicio == -1) {

            Toast.makeText(
                    this,
                    "No se ha recibido un ejercicio válido",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        cargarEjercicio();

        btnGuardarCambiosEjercicio.setOnClickListener(
                v -> guardarCambios()
        );

        btnCancelarEditarEjercicio.setOnClickListener(
                v -> finish()
        );
    }

    private void cargarEjercicio() {
        ejercicioActual =
                ejercicioRepository
                        .obtenerEjercicioPorId(idEjercicio);

        rutinaEjercicioActual =
                rutinaEjercicioRepository
                        .obtenerPorId(idRutinaEjercicio);

        if (ejercicioActual == null
                || rutinaEjercicioActual == null) {

            Toast.makeText(
                    this,
                    "No se ha encontrado el ejercicio",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        etNombreEjercicioEditar.setText(
                ejercicioActual.getNombre()
        );

        etGrupoMuscularEditar.setText(
                ejercicioActual.getGrupoMuscular()
        );

        etDescripcionEjercicioEditar.setText(
                ejercicioActual.getDescripcion()
        );

        etSeriesPlanificadasEditar.setText(
                String.valueOf(
                        rutinaEjercicioActual
                                .getSeriesPlanificadas()
                )
        );

        etRepeticionesPlanificadasEditar.setText(
                String.valueOf(
                        rutinaEjercicioActual
                                .getRepeticionesPlanificadas()
                )
        );

        etPesoObjetivoEditar.setText(
                formatearPeso(
                        rutinaEjercicioActual
                                .getPesoObjetivo()
                )
        );

        etDescansoSegundosEditar.setText(
                String.valueOf(
                        rutinaEjercicioActual
                                .getDescansoSegundos()
                )
        );
    }

    private void guardarCambios() {
        String nombre =
                etNombreEjercicioEditar
                        .getText()
                        .toString()
                        .trim();

        String grupoMuscular =
                etGrupoMuscularEditar
                        .getText()
                        .toString()
                        .trim();

        String descripcion =
                etDescripcionEjercicioEditar
                        .getText()
                        .toString()
                        .trim();

        String textoSeries =
                etSeriesPlanificadasEditar
                        .getText()
                        .toString()
                        .trim();

        String textoRepeticiones =
                etRepeticionesPlanificadasEditar
                        .getText()
                        .toString()
                        .trim();

        String textoPeso =
                etPesoObjetivoEditar
                        .getText()
                        .toString()
                        .trim();

        String textoDescanso =
                etDescansoSegundosEditar
                        .getText()
                        .toString()
                        .trim();

        if (nombre.isEmpty()) {
            Toast.makeText(
                    this,
                    "El nombre del ejercicio es obligatorio",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (textoSeries.isEmpty()
                || textoRepeticiones.isEmpty()
                || textoPeso.isEmpty()
                || textoDescanso.isEmpty()) {

            Toast.makeText(
                    this,
                    "Completa los datos de planificación",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        try {
            int series =
                    Integer.parseInt(textoSeries);

            int repeticiones =
                    Integer.parseInt(textoRepeticiones);

            double peso =
                    Double.parseDouble(textoPeso);

            int descanso =
                    Integer.parseInt(textoDescanso);

            if (series <= 0 || repeticiones <= 0) {
                Toast.makeText(
                        this,
                        "Las series y repeticiones deben ser mayores que cero",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (peso < 0 || descanso < 0) {
                Toast.makeText(
                        this,
                        "El peso y el descanso no pueden ser negativos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            boolean ejercicioActualizado =
                    ejercicioRepository.editarEjercicio(
                            idEjercicio,
                            nombre,
                            grupoMuscular,
                            descripcion
                    );

            boolean planificacionActualizada =
                    rutinaEjercicioRepository
                            .editarRutinaEjercicio(
                                    idRutinaEjercicio,
                                    series,
                                    repeticiones,
                                    peso,
                                    descanso
                            );

            if (ejercicioActualizado
                    && planificacionActualizada) {

                Toast.makeText(
                        this,
                        "Ejercicio actualizado correctamente",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {
                Toast.makeText(
                        this,
                        "No se pudo actualizar el ejercicio",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } catch (NumberFormatException exception) {
            Toast.makeText(
                    this,
                    "Introduce valores numéricos válidos",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private String formatearPeso(double peso) {
        if (peso == Math.floor(peso)) {
            return String.valueOf((int) peso);
        }

        return String.valueOf(peso);
    }
}