package com.example.gymtrack;

import android.app.Application;

import com.example.gymtrack.data.GymTrackDatabase;

public class GymTrackApplication
        extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GymTrackDatabase.inicializar(this);
    }
}