package com.example.gymtrack.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.gymtrack.data.dao.RutinaDao;
import com.example.gymtrack.model.Rutina;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RutinaDatabaseIntegrationTest {

    private GymTrackDatabase database;
    private RutinaDao rutinaDao;

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
    }

    @After
    public void cerrarBaseDeDatos() {
        if (database != null) {
            database.close();
        }
    }

    @Test
    public void insertarRutina_yConsultarListadoPorUsuario_devuelveRutinaGuardada() {
        int idUsuarioPrueba = 10;

        Date fechaCreacion =
                new Date(
                        1_710_000_000_000L
                );

        Rutina rutinaUsuario =
                new Rutina(
                        0,
                        idUsuarioPrueba,
                        "Rutina de integración",
                        "Rutina utilizada para probar Room",
                        "Fuerza",
                        fechaCreacion,
                        true
                );

        Rutina rutinaOtroUsuario =
                new Rutina(
                        0,
                        20,
                        "Rutina de otro usuario",
                        "No debe aparecer en el listado",
                        "Hipertrofia",
                        fechaCreacion,
                        true
                );

        long idGenerado =
                rutinaDao.insertar(
                        rutinaUsuario
                );

        rutinaDao.insertar(
                rutinaOtroUsuario
        );

        List<Rutina> rutinasRecuperadas =
                rutinaDao.obtenerPorUsuario(
                        idUsuarioPrueba
                );

        assertTrue(
                idGenerado > 0
        );

        assertEquals(
                1,
                rutinasRecuperadas.size()
        );

        Rutina rutinaRecuperada =
                rutinasRecuperadas.get(0);

        assertEquals(
                "Rutina de integración",
                rutinaRecuperada.getNombre()
        );

        assertEquals(
                "Rutina utilizada para probar Room",
                rutinaRecuperada.getDescripcion()
        );

        assertEquals(
                "Fuerza",
                rutinaRecuperada.getObjetivo()
        );

        assertEquals(
                idUsuarioPrueba,
                rutinaRecuperada.getIdUsuario()
        );

        assertEquals(
                fechaCreacion.getTime(),
                rutinaRecuperada
                        .getFechaCreacion()
                        .getTime()
        );

        assertTrue(
                rutinaRecuperada.isActiva()
        );
    }
}