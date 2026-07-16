package com.example.gymtrack.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.gymtrack.data.dao.EjercicioDao;
import com.example.gymtrack.data.dao.EntrenamientoDao;
import com.example.gymtrack.data.dao.RutinaDao;
import com.example.gymtrack.data.dao.RutinaEjercicioDao;
import com.example.gymtrack.data.dao.SerieEntrenamientoDao;
import com.example.gymtrack.model.Ejercicio;
import com.example.gymtrack.model.Entrenamiento;
import com.example.gymtrack.model.Rutina;
import com.example.gymtrack.model.RutinaEjercicio;
import com.example.gymtrack.model.SerieEntrenamiento;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class EntrenamientoHistorialDatabaseIntegrationTest {

    private GymTrackDatabase database;
    private RutinaDao rutinaDao;
    private EjercicioDao ejercicioDao;
    private RutinaEjercicioDao rutinaEjercicioDao;
    private EntrenamientoDao entrenamientoDao;
    private SerieEntrenamientoDao serieEntrenamientoDao;

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

        entrenamientoDao =
                database.entrenamientoDao();

        serieEntrenamientoDao =
                database.serieEntrenamientoDao();
    }

    @After
    public void cerrarBaseDeDatos() {
        if (database != null) {
            database.close();
        }
    }

    @Test
    public void guardarEntrenamientoConSeries_yConsultarHistorial_devuelveDatosCompletos() {
        int idUsuarioPrueba = 10;

        Date fechaRutina =
                new Date(
                        1_710_000_000_000L
                );

        Date fechaEntrenamiento =
                new Date(
                        1_720_000_000_000L
                );

        Rutina rutina =
                new Rutina(
                        0,
                        idUsuarioPrueba,
                        "Rutina historial",
                        "Rutina para prueba de integración",
                        "Fuerza",
                        fechaRutina,
                        true
                );

        long idRutina =
                rutinaDao.insertar(
                        rutina
                );

        Ejercicio ejercicio =
                new Ejercicio(
                        0,
                        "Press banca",
                        "Pecho",
                        "Ejercicio de empuje"
                );

        long idEjercicio =
                ejercicioDao.insertar(
                        ejercicio
                );

        RutinaEjercicio relacion =
                new RutinaEjercicio(
                        0,
                        (int) idRutina,
                        (int) idEjercicio,
                        1,
                        2,
                        10,
                        60.0,
                        90
                );

        long idRutinaEjercicio =
                rutinaEjercicioDao.insertar(
                        relacion
                );

        Entrenamiento entrenamiento =
                new Entrenamiento(
                        0,
                        idUsuarioPrueba,
                        (int) idRutina,
                        fechaEntrenamiento,
                        45,
                        "Entrenamiento completado"
                );

        long idEntrenamiento =
                entrenamientoDao.insertar(
                        entrenamiento
                );

        SerieEntrenamiento primeraSerie =
                new SerieEntrenamiento(
                        0,
                        (int) idEntrenamiento,
                        (int) idRutinaEjercicio,
                        1,
                        62.5,
                        8,
                        true
                );

        SerieEntrenamiento segundaSerie =
                new SerieEntrenamiento(
                        0,
                        (int) idEntrenamiento,
                        (int) idRutinaEjercicio,
                        2,
                        60.0,
                        10,
                        true
                );

        serieEntrenamientoDao.insertar(
                primeraSerie
        );

        serieEntrenamientoDao.insertar(
                segundaSerie
        );

        Entrenamiento entrenamientoOtroUsuario =
                new Entrenamiento(
                        0,
                        20,
                        (int) idRutina,
                        fechaEntrenamiento,
                        30,
                        "No debe aparecer"
                );

        entrenamientoDao.insertar(
                entrenamientoOtroUsuario
        );

        List<Entrenamiento> historialUsuario =
                entrenamientoDao.obtenerPorUsuario(
                        idUsuarioPrueba
                );

        assertEquals(
                1,
                historialUsuario.size()
        );

        Entrenamiento entrenamientoRecuperado =
                historialUsuario.get(0);

        assertEquals(
                idEntrenamiento,
                entrenamientoRecuperado
                        .getIdEntrenamiento()
        );

        assertEquals(
                idUsuarioPrueba,
                entrenamientoRecuperado
                        .getIdUsuario()
        );

        assertEquals(
                idRutina,
                entrenamientoRecuperado
                        .getIdRutina()
        );

        assertEquals(
                fechaEntrenamiento.getTime(),
                entrenamientoRecuperado
                        .getFecha()
                        .getTime()
        );

        assertEquals(
                45,
                entrenamientoRecuperado
                        .getDuracionMinutos()
        );

        assertEquals(
                "Entrenamiento completado",
                entrenamientoRecuperado
                        .getObservaciones()
        );

        List<SerieEntrenamiento> seriesRecuperadas =
                serieEntrenamientoDao
                        .obtenerPorEntrenamiento(
                                (int) idEntrenamiento
                        );

        assertEquals(
                2,
                seriesRecuperadas.size()
        );

        SerieEntrenamiento serieUno =
                seriesRecuperadas.get(0);

        assertEquals(
                1,
                serieUno.getNumeroSerie()
        );

        assertEquals(
                62.5,
                serieUno.getPeso(),
                0.001
        );

        assertEquals(
                8,
                serieUno.getRepeticiones()
        );

        assertTrue(
                serieUno.isCompletada()
        );

        SerieEntrenamiento serieDos =
                seriesRecuperadas.get(1);

        assertEquals(
                2,
                serieDos.getNumeroSerie()
        );

        assertEquals(
                60.0,
                serieDos.getPeso(),
                0.001
        );

        assertEquals(
                10,
                serieDos.getRepeticiones()
        );

        assertTrue(
                serieDos.isCompletada()
        );
    }
}