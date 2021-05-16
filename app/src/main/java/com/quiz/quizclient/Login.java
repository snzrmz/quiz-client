package com.quiz.quizclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Login extends AppCompatActivity {

    private static final String CLAVE_LOGINCORRECTO = "loginCorrecto";
    private static final String CLAVE_IDJUGADOR = "idJugador";
    private boolean esLoginCorrecto;
    private int idJugador;
    private SharedPreferences preferencias;
    private MaterialButton botonLogin;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Inicio sesión");
        setContentView(R.layout.activity_login);

        obtenerPreferencias();
    }

    private void obtenerPreferencias() {
        preferencias = getPreferences(MODE_PRIVATE);
        esLoginCorrecto = preferencias.getBoolean(CLAVE_LOGINCORRECTO, false);
        idJugador = preferencias.getInt(CLAVE_IDJUGADOR, -1);
    }

    private void guardarPreferencias() {
        SharedPreferences.Editor editor;
        editor = preferencias.edit();
        editor.putBoolean(CLAVE_LOGINCORRECTO, esLoginCorrecto);
        editor.apply();
    }

    private boolean loginCorrecto() {
        return true;
    }

    private void onComprobarLogin(View v) {
        //Intent a la activity que llevará si login es correcto
        Intent intent = new Intent(this, Menu.class);
        if (loginCorrecto()) {
            esLoginCorrecto = true;
            guardarPreferencias();
            //iniciando actividad
            intent.putExtra(CLAVE_IDJUGADOR, idJugador);
            startActivity(intent);

        } else {
            esLoginCorrecto = false;
            guardarPreferencias();
        }

    }
}