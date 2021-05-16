package com.quiz.quizclient;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizclient.modelo.API;
import com.quiz.quizclient.modelo.Jugador;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Perfil extends AppCompatActivity {
TextView jugador_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setTitle("Mi Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getJugador();
    }
    private void getJugador() {
        jugador_info = findViewById(R.id.info_jugador);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.42:8080/quiz-server/api/jugadores/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        Call<Jugador> call = api.getJugador();
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

}