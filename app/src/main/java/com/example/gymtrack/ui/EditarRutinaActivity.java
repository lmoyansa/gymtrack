package com.example.gymtrack.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.model.Rutina;
import com.example.gymtrack.repository.RutinaRepository;

public class EditarRutinaActivity extends Activity {

    public static final String EXTRA_ID_RUTINA = "idRutina";

    private EditText etNombreRutinaEditar;
    private EditText etDescripcionRutinaEditar;
    private EditText etObjetivoRutinaEditar;
    private Button btnGuardarCambiosRutina;
    private Button btnCancelarEditarRutina;

    private RutinaRepository rutinaRepository;
    private Rutina rutinaActual;
    private int idRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rutina);

        etNombreRutinaEditar = findViewById(R.id.etNombreRutinaEditar);
        etDescripcionRutinaEditar = findViewById(R.id.etDescripcionRutinaEditar);
        etObjetivoRutinaEditar = findViewById(R.id.etObjetivoRutinaEditar);
        btnGuardarCambiosRutina = findViewById(R.id.btnGuardarCambiosRutina);
        btnCancelarEditarRutina = findViewById(R.id.btnCancelarEditarRutina);

        rutinaRepository = RutinaRepository.getInstance();

        idRutina = getIntent().getIntExtra(EXTRA_ID_RUTINA, -1);
        rutinaActual = rutinaRepository.obtenerRutinaPorId(idRutina);

        if (rutinaActual == null) {
            Toast.makeText(this, "No se ha encontrado la rutina", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarDatosRutina();

        btnGuardarCambiosRutina.setOnClickListener(v -> guardarCambios());

        btnCancelarEditarRutina.setOnClickListener(v -> finish());
    }

    private void cargarDatosRutina() {
        etNombreRutinaEditar.setText(rutinaActual.getNombre());
        etDescripcionRutinaEditar.setText(rutinaActual.getDescripcion());
        etObjetivoRutinaEditar.setText(rutinaActual.getObjetivo());
    }

    private void guardarCambios() {
        String nombre = etNombreRutinaEditar.getText().toString().trim();
        String descripcion = etDescripcionRutinaEditar.getText().toString().trim();
        String objetivo = etObjetivoRutinaEditar.getText().toString().trim();

        if (!rutinaRepository.validarNombreRutina(nombre)) {
            Toast.makeText(this, "El nombre de la rutina es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean editada = rutinaRepository.editarRutina(idRutina, nombre, descripcion, objetivo);

        if (editada) {
            Toast.makeText(this, "Rutina actualizada correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No se pudo actualizar la rutina", Toast.LENGTH_SHORT).show();
        }
    }
}