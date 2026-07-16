package com.example.gymtrack.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.gymtrack.data.dao.EjercicioDao;
import com.example.gymtrack.data.dao.RutinaDao;
import com.example.gymtrack.data.dao.RutinaEjercicioDao;
import com.example.gymtrack.model.Ejercicio;
import com.example.gymtrack.model.Rutina;
import com.example.gymtrack.model.RutinaEjercicio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class EjercicioRutinaDatabaseIntegrationTest {

    private GymTrackDatabase database;
    private RutinaDao rutinaDao;
    private EjercicioDao ejercicioDao;
    private RutinaEjercicioDao rutinaEjercicioDao;

    @Before
    public void prepararBaseDeDatos() {
        Context context =
                ApplicationProvider
                        .getApplicationContext();

        database =
                Room.inMemoryDatabaseBuilder(
                                context,
                                GymTrackDatabase.class
                        )
                        .allowMainThreadQueries()
                        .build();

        rutinaDao =
                database.rutinaDao();

        ejercicioDao =
                database.ejercicioDao();

        rutinaEjercicioDao =
                database.rutinaEjercicioDao();
    }

    @After
    public void cerrarBaseDeDatos() {
        if (database != null) {
            database.close();
        }
    }

    @Test
    public void añadirEjercicioARutina_yConsultarDetalle_devuelveDatosAsociados() {
        Rutina rutina =
                new Rutina(
                        0,
                        10,
                        "Rutina de fuerza",
                        "Rutina utilizada en la prueba",
                        "Fuerza",
                        new Date(
                                1_710_000_000_000L
                        ),
                        true
                );

        long idRutinaGenerado =
                rutinaDao.insertar(
                        rutina
                );

        Ejercicio ejercicio =
                new Ejercicio(
                        0,
                        "Press banca",
                        "Pecho",
                        "Ejercicio principal de empuje"
                );

        long idEjercicioGenerado =
                ejercicioDao.insertar(
                        ejercicio
                );

        RutinaEjercicio relacion =
                new RutinaEjercicio(
                        0,
                        (int) idRutinaGenerado,
                        (int) idEjercicioGenerado,
                        1,
                        4,
                        10,
                        60.0,
                        90
                );

        long idRelacionGenerado =
                rutinaEjercicioDao.insertar(
                        relacion
                );

        List<RutinaEjercicio> relacionesRecuperadas =
                rutinaEjercicioDao.obtenerPorRutina(
                        (int) idRutinaGenerado
                );

        assertTrue(
                idRutinaGenerado > 0
        );

        assertTrue(
                idEjercicioGenerado > 0
        );

        assertTrue(
                idRelacionGenerado > 0
        );

        assertEquals(
                1,
                relacionesRecuperadas.size()
        );

        RutinaEjercicio relacionRecuperada =
                relacionesRecuperadas.get(0);

        assertEquals(
                idRutinaGenerado,
                relacionRecuperada.getIdRutina()
        );

        assertEquals(
                idEjercicioGenerado,
                relacionRecuperada.getIdEjercicio()
        );

        assertEquals(
                1,
                relacionRecuperada.getOrden()
        );

        assertEquals(
                4,
                relacionRecuperada.getSeriesPlanificadas()
        );

        assertEquals(
                10,
                relacionRecuperada
                        .getRepeticionesPlanificadas()
        );

        assertEquals(
                60.0,
                relacionRecuperada.getPesoObjetivo(),
                0.001
        );

        assertEquals(
                90,
                relacionRecuperada.getDescansoSegundos()
        );

        Ejercicio ejercicioRecuperado =
                ejercicioDao.obtenerPorId(
                        relacionRecuperada
                                .getIdEjercicio()
                );

        assertEquals(
                "Press banca",
                ejercicioRecuperado.getNombre()
        );

        assertEquals(
                "Pecho",
                ejercicioRecuperado.getGrupoMuscular()
        );

        assertEquals(
                "Ejercicio principal de empuje",
                ejercicioRecuperado.getDescripcion()
        );
    }
}