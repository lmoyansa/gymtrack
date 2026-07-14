package com.example.gymtrack.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String
            NOMBRE_PREFERENCIAS =
            "gymtrack_session";

    private static final String
            CLAVE_ID_USUARIO =
            "idUsuario";

    private static final int
            SIN_USUARIO =
            -1;

    private final SharedPreferences
            preferences;

    public SessionManager(Context context) {
        preferences =
                context
                        .getApplicationContext()
                        .getSharedPreferences(
                                NOMBRE_PREFERENCIAS,
                                Context.MODE_PRIVATE
                        );
    }

    public void iniciarSesion(int idUsuario) {
        preferences
                .edit()
                .putInt(
                        CLAVE_ID_USUARIO,
                        idUsuario
                )
                .apply();
    }

    public void cerrarSesion() {
        preferences
                .edit()
                .remove(CLAVE_ID_USUARIO)
                .apply();
    }

    public boolean haySesionIniciada() {
        return obtenerIdUsuario()
                != SIN_USUARIO;
    }

    public int obtenerIdUsuario() {
        return preferences.getInt(
                CLAVE_ID_USUARIO,
                SIN_USUARIO
        );
    }
}