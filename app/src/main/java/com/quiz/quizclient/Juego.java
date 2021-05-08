package com.quiz.quizclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.quiz.quizclient.modelo.jugador;
import com.quiz.quizclient.modelo.interfaz;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Juego extends AppCompatActivity {
TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        getPosts();
    }
    private void getPosts(){
        texto = findViewById(R.id.texto1);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.138:8080/quiz-server/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            interfaz interfz = retrofit.create(interfaz.class);

        Call<List<jugador>> call = interfz.getPosts();
        call.enqueue(new Callback<List<jugador>>() {
            @Override
            public void onResponse(Call<List<jugador>> call, Response<List<jugador>> response) {
                List<jugador> lista = response.body();
                for(jugador obj: lista){
                    String contenido = "";
                    contenido += "E-mail: "+ obj.getEmail()+"\n";
                    contenido += "Se registró el: "+ obj.getFechaCreacion()+"\n";
                    contenido += "ID: "+ obj.getIdJugador()+"\n";
                    contenido += "Usuario: "+ obj.getUsuario()+"\n\n";
                    texto.append(contenido);
                }
            }

            @Override
            public void onFailure(Call<List<jugador>> call, Throwable t) {
                texto = findViewById(R.id.texto1);
                texto.setText(t.getMessage());
            }
        });

    }
}