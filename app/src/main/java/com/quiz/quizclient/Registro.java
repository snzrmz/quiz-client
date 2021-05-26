package com.quiz.quizclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro extends AppCompatActivity {

    EditText email,name,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registro");

        email = findViewById(R.id.inputemail);
        name = findViewById(R.id.user_input);
        pass = findViewById(R.id.password_edit_text);
    }


    public void addJugador(View v){
        API api = Client.getClient().create(API.class);
        Jugador jugador =  new Jugador();
        jugador.setEmail(email.getText().toString());
        jugador.setUsuario(name.getText().toString());
        jugador.setPassword(pass.getText().toString());
        Call<Jugador> call = api.CreateJugador(new Jugador());
        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                //se podria obtener el idjugdador y abrir directamente menu
                Snackbar.make(v, "¡Registro correcto!", Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {

            }
        });
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }

}