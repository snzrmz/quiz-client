package com.quiz.quizclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro extends AppCompatActivity {

    TextInputEditText email, name, pass;
    int idJugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registro");

        email = findViewById(R.id.inputemail);
        name = findViewById(R.id.user_input);
        pass = findViewById(R.id.password_edit_text);
        idJugador = -1;
    }


    public void addJugador(View v) {
        API api = Client.getClient().create(API.class);
        LocalDate localDate = LocalDate.now();
        String fechaFormateada = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Jugador jugador = new Jugador();
        jugador.setEmail(email.getText().toString());
        jugador.setUsuario(name.getText().toString());
        jugador.setPassword(pass.getText().toString());
        jugador.setFechaCreacion(fechaFormateada);


        Log.d("LOG", jugador.toString());
        Call<Void> call = api.createJugador(jugador);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //se podria obtener el idjugdador y abrir directamente menu

                if (response.isSuccessful()) {
                    String[] location = response.headers().get("Location").split("/");
                    idJugador = Integer.parseInt(location[location.length - 1]);
                    //Snackbar.make(v, "¡Registrado Correctamente! ", Snackbar.LENGTH_SHORT).show();

                    /*https://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android*/
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    intent.putExtra("nuevoJugador", true);
                    intent.putExtra("idJugador", idJugador);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {

                }

            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Snackbar.make(v, "Fallo al registrar " + t.getMessage(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });


    }

}