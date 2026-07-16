package com.example.gymtrack.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.gymtrack.R;
import com.example.gymtrack.data.GymTrackDatabase;
import com.example.gymtrack.data.dao.UsuarioDao;
import com.example.gymtrack.model.Usuario;
import com.example.gymtrack.session.SessionManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class NavegacionPrincipalIntegrationTest {

    private UsuarioDao usuarioDao;
    private SessionManager sessionManager;
    private ActivityScenario<MainActivity> scenario;
    private Usuario usuarioPrueba;

    @Before
    public void prepararUsuarioYSesion() {
        Context context =
                ApplicationProvider
                        .getApplicationContext();

        GymTrackDatabase database =
                GymTrackDatabase.obtenerInstancia(
                        context
                );

        usuarioDao =
                database.usuarioDao();

        usuarioPrueba =
                new Usuario(
                        0,
                        "Usuario PI-04",
                        "pi04@gymtrack.test",
                        "password-prueba",
                        new Date()
                );

        long idUsuario =
                usuarioDao.insertar(
                        usuarioPrueba
                );

        usuarioPrueba.setIdUsuario(
                (int) idUsuario
        );

        sessionManager =
                new SessionManager(
                        context
                );

        sessionManager.iniciarSesion(
                (int) idUsuario
        );

        scenario =
                ActivityScenario.launch(
                        MainActivity.class
                );
    }

    @After
    public void limpiarDatos() {
        if (scenario != null) {
            scenario.close();
        }

        if (sessionManager != null) {
            sessionManager.cerrarSesion();
        }

        if (usuarioDao != null
                && usuarioPrueba != null) {

            usuarioDao.eliminar(
                    usuarioPrueba
            );
        }
    }

    @Test
    public void navegarDesdeInicio_aPantallasPrincipales_funcionaCorrectamente() {
        onView(
                withId(
                        R.id.btnMisRutinas
                )
        ).perform(
                click()
        );

        onView(
                withId(
                        R.id.btnCrearRutina
                )
        ).check(
                matches(
                        isDisplayed()
                )
        );

        pressBack();

        onView(
                withId(
                        R.id.btnHistorial
                )
        ).perform(
                click()
        );

        onView(
                withId(
                        R.id.btnVolverInicioHistorial
                )
        ).check(
                matches(
                        isDisplayed()
                )
        );

        pressBack();

        onView(
                withId(
                        R.id.btnTemporizador
                )
        ).perform(
                click()
        );

        onView(
                withId(
                        R.id.btnTiempo60
                )
        ).check(
                matches(
                        isDisplayed()
                )
        );

        pressBack();

        onView(
                withId(
                        R.id.btnMisRutinas
                )
        ).check(
                matches(
                        isDisplayed()
                )
        );
    }
}