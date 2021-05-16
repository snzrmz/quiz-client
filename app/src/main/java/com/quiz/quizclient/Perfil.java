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
    TextView jugador_usuario, jugador_fechaCreacion;

    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setTitle("Mi Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getJugador(getIntent().getIntExtra("idJugador", -1));

        jugador_usuario = findViewById(R.id.txtUsuario);
        jugador_fechaCreacion = findViewById(R.id.txtFechaCreacion);
    }

    private void getJugador(int idJugador) {


        api = Client.getClient().create(API.class);

        Call<Jugador> call = api.getJugadorById(idJugador);
        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                jugador_usuario.setText(response.body().getUsuario());
                jugador_fechaCreacion.setText(response.body().getFechaCreacion());

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