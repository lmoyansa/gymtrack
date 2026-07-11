package com.example.gymtrack.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.model.Ejercicio;
import com.example.gymtrack.repository.EjercicioRepository;
import com.example.gymtrack.repository.RutinaEjercicioRepository;

public class AnadirEjercicioActivity extends Activity {

    public static final String EXTRA_ID_RUTINA = "idRutina";

    private EditText etNombreEjercicio;
    private EditText etGrupoMuscular;
    private EditText etDescripcionEjercicio;
    private EditText etSeriesPlanificadas;
    private EditText etRepeticionesPlanificadas;
    private EditText etPesoObjetivo;
    private EditText etDescansoSegundos;

    private EjercicioRepository ejercicioRepository;
    private RutinaEjercicioRepository rutinaEjercicioRepository;

    private int idRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_ejercicio);

        etNombreEjercicio = findViewById(R.id.etNombreEjercicio);
        etGrupoMuscular = findViewById(R.id.etGrupoMuscular);
        etDescripcionEjercicio = findViewById(R.id.etDescripcionEjercicio);
        etSeriesPlanificadas = findViewById(R.id.etSeriesPlanificadas);
        etRepeticionesPlanificadas = findViewById(R.id.etRepeticionesPlanificadas);
        etPesoObjetivo = findViewById(R.id.etPesoObjetivo);
        etDescansoSegundos = findViewById(R.id.etDescansoSegundos);

        Button btnGuardarEjercicio = findViewById(R.id.btnGuardarEjercicio);
        Button btnCancelarAnadirEjercicio =
                findViewById(R.id.btnCancelarAnadirEjercicio);

        ejercicioRepository = EjercicioRepository.getInstance();
        rutinaEjercicioRepository = RutinaEjercicioRepository.getInstance();

        idRutina = getIntent().getIntExtra(EXTRA_ID_RUTINA, -1);

        if (idRutina == -1) {
            Toast.makeText(
                    this,
                    "No se ha recibido una rutina válida",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        btnGuardarEjercicio.setOnClickListener(v -> guardarEjercicio());
        btnCancelarAnadirEjercicio.setOnClickListener(v -> finish());
    }

    private void guardarEjercicio() {
        String nombre = etNombreEjercicio.getText().toString().trim();
        String grupoMuscular = etGrupoMuscular.getText().toString().trim();
        String descripcion = etDescripcionEjercicio.getText().toString().trim();

        String textoSeries =
                etSeriesPlanificadas.getText().toString().trim();

        String textoRepeticiones =
                etRepeticionesPlanificadas.getText().toString().trim();

        String textoPeso =
                etPesoObjetivo.getText().toString().trim();

        String textoDescanso =
                etDescansoSegundos.getText().toString().trim();

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
            int series = Integer.parseInt(textoSeries);
            int repeticiones = Integer.parseInt(textoRepeticiones);
            double peso = Double.parseDouble(textoPeso);
            int descanso = Integer.parseInt(textoDescanso);

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

            Ejercicio ejercicio = ejercicioRepository.crearEjercicio(
                    nombre,
                    grupoMuscular,
                    descripcion
            );

            rutinaEjercicioRepository.crearRutinaEjercicio(
                    idRutina,
                    ejercicio.getIdEjercicio(),
                    series,
                    repeticiones,
                    peso,
                    descanso
            );

            Toast.makeText(
                    this,
                    "Ejercicio añadido correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } catch (NumberFormatException exception) {
            Toast.makeText(
                    this,
                    "Introduce valores numéricos válidos",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}