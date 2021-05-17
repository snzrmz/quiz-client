package com.quiz.quizclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {
    private final static String STATE_LOGINSTATUS = "esLoginCorrecto";
    private final static String STATE_IDJUGADOR = "idJugador";

    TextView jugador_usuario, jugador_fechaCreacion;
    String usuario, fecha;
    int idJugador;
    SharedPreferences preferencias;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setTitle("Mi Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //cogemos directamente el idJugador de sharedpreferences,
        // no del intent, ya que al ir hacia atras
        //puede retornar el valor por defecto (-1)
        preferencias = getSharedPreferences("IDvalue", 0);
        idJugador = preferencias.getInt(STATE_IDJUGADOR, -1);
        getJugador(idJugador);
        jugador_usuario = findViewById(R.id.txtUsuario);
        jugador_fechaCreacion = findViewById(R.id.txtFechaCreacion);

    }

    private void getJugador(int idJugador) {
        api = Client.getClient().create(API.class);
        Call<Jugador> call = api.getJugadorById(idJugador);
        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                usuario = response.body().getUsuario();
                fecha = response.body().getFechaCreacion();
                jugador_usuario.setText(usuario);
                jugador_fechaCreacion.setText(fecha);
            }


            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onCerrarSesion(View v) {
        //obtenemos las preferencias de Login
        SharedPreferences preferencias = getSharedPreferences("IDvalue", 0);
        //editamos las preferencias y establecemos esLoginCorrecto a false
        Log.println(Log.DEBUG, "DEBUG_STATE_CREATE", String.valueOf(preferencias.getBoolean(STATE_LOGINSTATUS, false)));
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean(STATE_LOGINSTATUS, false);
        editor.apply();

        //volvemos a la pantalla de login
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

}