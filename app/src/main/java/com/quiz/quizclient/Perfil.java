package com.quiz.quizclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {
    TextView jugador_info;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setTitle("Mi Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getJugador(1);
    }

    private void getJugador(int idJugador) {
        jugador_info = findViewById(R.id.info_jugador);

        api = Client.getClient().create(API.class);

        Call<Jugador> call = api.getJugadorById(idJugador);
        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {

                String contenido = "";
                contenido += "E-mail: " + response.body().getEmail() + "\n";
                contenido += "ID: " + response.body().getIdJugador() + "\n";
                contenido += "Usuario: " + response.body().getUsuario() + "\n";
                contenido += "Contraseña: " + response.body().getPassword() + "\n";
                // contenido += "Se registró el: " + response.body().getFechaCreacion()+ "\n\n";
                jugador_info.append(contenido);
            }


            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {

                jugador_info.setText(t.getMessage());
            }
        });
    }

    public void onCerrarSesion(View v) {
        //obtenemos las preferencias de Login
        SharedPreferences preferencias = getSharedPreferences("IDvalue", 0);
        //editamos las preferencias y establecemos esLoginCorrecto a false
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean("esLoginCorrecto", false);
        editor.apply();
        //volvemos a la pantalla de login
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

}