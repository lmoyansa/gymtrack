package com.example.gymtrack.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.model.Rutina;
import com.example.gymtrack.repository.RutinaRepository;

public class DetalleRutinaActivity extends Activity {

    public static final String EXTRA_ID_RUTINA = "idRutina";

    private TextView tvNombreRutinaDetalle;
    private TextView tvObjetivoRutinaDetalle;
    private TextView tvDescripcionRutinaDetalle;

    private Button btnEditarRutina;
    private Button btnEliminarRutina;
    private Button btnAnadirEjercicio;
    private Button btnEmpezarEntrenamiento;
    private Button btnVolverListadoRutinas;

    private RutinaRepository rutinaRepository;
    private Rutina rutinaActual;
    private int idRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_rutina);

        tvNombreRutinaDetalle = findViewById(R.id.tvNombreRutinaDetalle);
        tvObjetivoRutinaDetalle = findViewById(R.id.tvObjetivoRutinaDetalle);
        tvDescripcionRutinaDetalle = findViewById(R.id.tvDescripcionRutinaDetalle);

        btnEditarRutina = findViewById(R.id.btnEditarRutina);
        btnEliminarRutina = findViewById(R.id.btnEliminarRutina);
        btnAnadirEjercicio = findViewById(R.id.btnAnadirEjercicio);
        btnEmpezarEntrenamiento = findViewById(R.id.btnEmpezarEntrenamiento);
        btnVolverListadoRutinas = findViewById(R.id.btnVolverListadoRutinas);

        rutinaRepository = RutinaRepository.getInstance();

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

        cargarRutina();

        btnEditarRutina.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarRutinaActivity.class);
            intent.putExtra(
                    EditarRutinaActivity.EXTRA_ID_RUTINA,
                    rutinaActual.getIdRutina()
            );
            startActivity(intent);
        });

        btnEliminarRutina.setOnClickListener(v ->
                mostrarConfirmacionEliminar()
        );

        btnAnadirEjercicio.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "Añadir ejercicio pendiente de implementar",
                        Toast.LENGTH_SHORT
                ).show()
        );

        btnEmpezarEntrenamiento.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "Registro de entrenamiento pendiente de implementar",
                        Toast.LENGTH_SHORT
                ).show()
        );

        btnVolverListadoRutinas.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (rutinaRepository != null && idRutina != -1) {
            cargarRutina();
        }
    }

    private void cargarRutina() {
        rutinaActual = rutinaRepository.obtenerRutinaPorId(idRutina);

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
        tvNombreRutinaDetalle.setText(rutinaActual.getNombre());

        String objetivo = rutinaActual.getObjetivo();

        if (objetivo == null || objetivo.trim().isEmpty()) {
            tvObjetivoRutinaDetalle.setText("Objetivo: no especificado");
        } else {
            tvObjetivoRutinaDetalle.setText("Objetivo: " + objetivo);
        }

        String descripcion = rutinaActual.getDescripcion();

        if (descripcion == null || descripcion.trim().isEmpty()) {
            tvDescripcionRutinaDetalle.setText("Descripción: no especificada");
        } else {
            tvDescripcionRutinaDetalle.setText("Descripción: " + descripcion);
        }
    }

    private void mostrarConfirmacionEliminar() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar rutina")
                .setMessage(
                        "¿Seguro que quieres eliminar la rutina \""
                                + rutinaActual.getNombre()
                                + "\"?"
                )
                .setPositiveButton("Eliminar", (dialog, which) -> eliminarRutina())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarRutina() {
        boolean eliminada = rutinaRepository.eliminarRutina(idRutina);

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
}