package com.example.gymtrack.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.gymtrack.model.Rutina;
import com.example.gymtrack.model.Usuario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class PersistenciaDatabaseIntegrationTest {

    private static final String NOMBRE_BASE_DATOS_PRUEBA =
            "pi05_persistencia_database";

    private Context context;
    private GymTrackDatabase database;

    @Before
    public void prepararPrueba() {
        context =
                ApplicationProvider
                        .getApplicationContext();

        context.deleteDatabase(
                NOMBRE_BASE_DATOS_PRUEBA
        );
    }

    @After
    public void limpiarPrueba() {
        if (database != null
                && database.isOpen()) {

            database.close();
        }

        context.deleteDatabase(
                NOMBRE_BASE_DATOS_PRUEBA
        );
    }

    @Test
    public void cerrarYReabrirBaseDeDatos_conservaUsuarioYRutina() {
        Date fechaPrueba =
                new Date(
                        1_710_000_000_000L
                );

        database =
                abrirBaseDeDatos();

        Usuario usuario =
                new Usuario(
                        0,
                        "Usuario persistencia",
                        "pi05@gymtrack.test",
                        "password-prueba",
                        fechaPrueba
                );

        long idUsuario =
                database
                        .usuarioDao()
                        .insertar(
                                usuario
                        );

        Rutina rutina =
                new Rutina(
                        0,
                        (int) idUsuario,
                        "PI-05 Rutina persistente",
                        "Debe conservarse tras reabrir Room",
                        "Fuerza",
                        fechaPrueba,
                        true
                );

        long idRutina =
                database
                        .rutinaDao()
                        .insertar(
                                rutina
                        );

        assertTrue(
                idUsuario > 0
        );

        assertTrue(
                idRutina > 0
        );

        database.close();
        database = null;

        database =
                abrirBaseDeDatos();

        Usuario usuarioRecuperado =
                database
                        .usuarioDao()
                        .obtenerPorId(
                                (int) idUsuario
                        );

        Rutina rutinaRecuperada =
                database
                        .rutinaDao()
                        .obtenerPorIdYUsuario(
                                (int) idRutina,
                                (int) idUsuario
                        );

        assertNotNull(
                usuarioRecuperado
        );

        assertNotNull(
                rutinaRecuperada
        );

        assertEquals(
                "Usuario persistencia",
                usuarioRecuperado.getNombre()
        );

        assertEquals(
                "pi05@gymtrack.test",
                usuarioRecuperado.getEmail()
        );

        assertEquals(
                fechaPrueba.getTime(),
                usuarioRecuperado
                        .getFechaRegistro()
                        .getTime()
        );

        assertEquals(
                "PI-05 Rutina persistente",
                rutinaRecuperada.getNombre()
        );

        assertEquals(
                "Debe conservarse tras reabrir Room",
                rutinaRecuperada.getDescripcion()
        );

        assertEquals(
                "Fuerza",
                rutinaRecuperada.getObjetivo()
        );

        assertEquals(
                idUsuario,
                rutinaRecuperada.getIdUsuario()
        );

        assertEquals(
                fechaPrueba.getTime(),
                rutinaRecuperada
                        .getFechaCreacion()
                        .getTime()
        );

        assertTrue(
                rutinaRecuperada.isActiva()
        );
    }

    private GymTrackDatabase abrirBaseDeDatos() {
        return Room.databaseBuilder(
                        context,
                        GymTrackDatabase.class,
                        NOMBRE_BASE_DATOS_PRUEBA
                )
                .allowMainThreadQueries()
                .build();
    }
}