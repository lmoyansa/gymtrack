package com.example.gymtrack.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.gymtrack.data.dao.EjercicioDao;
import com.example.gymtrack.data.dao.EntrenamientoDao;
import com.example.gymtrack.data.dao.RutinaDao;
import com.example.gymtrack.data.dao.RutinaEjercicioDao;
import com.example.gymtrack.data.dao.SerieEntrenamientoDao;
import com.example.gymtrack.data.dao.UsuarioDao;
import com.example.gymtrack.model.Ejercicio;
import com.example.gymtrack.model.Entrenamiento;
import com.example.gymtrack.model.Rutina;
import com.example.gymtrack.model.RutinaEjercicio;
import com.example.gymtrack.model.SerieEntrenamiento;
import com.example.gymtrack.model.Usuario;

@Database(
        entities = {
                Usuario.class,
                Rutina.class,
                Ejercicio.class,
                RutinaEjercicio.class,
                Entrenamiento.class,
                SerieEntrenamiento.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters(DateConverter.class)
public abstract class GymTrackDatabase
        extends RoomDatabase {

    private static final String NOMBRE_BASE_DATOS =
            "gymtrack_database";

    private static volatile
    GymTrackDatabase instance;

    public abstract UsuarioDao usuarioDao();

    public abstract RutinaDao rutinaDao();

    public abstract EjercicioDao ejercicioDao();

    public abstract RutinaEjercicioDao
    rutinaEjercicioDao();

    public abstract EntrenamientoDao
    entrenamientoDao();

    public abstract SerieEntrenamientoDao
    serieEntrenamientoDao();

    public static void inicializar(
            Context context
    ) {
        obtenerInstancia(context);
    }

    public static GymTrackDatabase
    obtenerInstancia() {

        if (instance == null) {
            throw new IllegalStateException(
                    "La base de datos no ha sido inicializada"
            );
        }

        return instance;
    }

    public static GymTrackDatabase
    obtenerInstancia(Context context) {

        if (instance == null) {
            synchronized (
                    GymTrackDatabase.class
            ) {
                if (instance == null) {
                    instance =
                            Room.databaseBuilder(
                                            context
                                                    .getApplicationContext(),
                                            GymTrackDatabase.class,
                                            NOMBRE_BASE_DATOS
                                    )
                                    .allowMainThreadQueries()
                                    .build();
                }
            }
        }

        return instance;
    }
}