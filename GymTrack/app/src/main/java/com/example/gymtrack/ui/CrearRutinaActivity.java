package com.example.gymtrack.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymtrack.R;
import com.example.gymtrack.repository.RutinaRepository;

public class CrearRutinaActivity extends Activity {

    private EditText etNombreRutina;
    private EditText etDescripcionRutina;
    private EditText etObjetivoRutina;
    private Button btnGuardarRutina;
    private Button btnCancelarCrearRutina;

    private RutinaRepository rutinaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina);

        etNombreRutina = findViewById(R.id.etNombreRutina);
        etDescripcionRutina = findViewById(R.id.etDescripcionRutina);
        etObjetivoRutina = findViewById(R.id.etObjetivoRutina);
        btnGuardarRutina = findViewById(R.id.btnGuardarRutina);
        btnCancelarCrearRutina = findViewById(R.id.btnCancelarCrearRutina);

        rutinaRepository = RutinaRepository.getInstance();

        btnGuardarRutina.setOnClickListener(v -> guardarRutina());

        btnCancelarCrearRutina.setOnClickListener(v -> finish());
    }

    private void guardarRutina() {
        String nombre = etNombreRutina.getText().toString().trim();
        String descripcion = etDescripcionRutina.getText().toString().trim();
        String objetivo = etObjetivoRutina.getText().toString().trim();

        if (!rutinaRepository.validarNombreRutina(nombre)) {
            Toast.makeText(this, "El nombre de la rutina es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        rutinaRepository.crearRutina(nombre, descripcion, objetivo);

        Toast.makeText(this, "Rutina creada correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }
}