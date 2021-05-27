package com.quiz.quizclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

    TextView jugador_usuario, jugador_fechaCreacion,jugador_email;
    String usuario, fecha, email;
    int idJugador;
    SharedPreferences preferencias;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setTitle("Mi Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idJugador = getIntent().getIntExtra("idJugador", -1);
        getJugador(idJugador);
        jugador_usuario = findViewById(R.id.txtUsuario);
        jugador_fechaCreacion = findViewById(R.id.txtFechaCreacion);
        jugador_email = findViewById(R.id.txtEmail);

    }

    private void getJugador(int idJugador) {
        api = Client.getClient().create(API.class);
        Call<Jugador> call = api.getJugadorById(idJugador);
        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                usuario = response.body().getUsuario();
                fecha = response.body().getFechaCreacion();
                email = response.body().getEmail();
                jugador_usuario.setText(usuario);
                jugador_fechaCreacion.setText("Primer inicio de sesión el "+fecha.toString());
                jugador_email.setText(email);
            }


            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onCerrarSesion(View v) {
        preferencias = getSharedPreferences("IDvalue", 0);
        //editamos las preferencias y establecemos esLoginCorrecto a false
        Log.println(Log.DEBUG, "DEBUG_STATE_CREATE", String.valueOf(preferencias.getBoolean(STATE_LOGINSTATUS, false)));

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean(STATE_LOGINSTATUS, false);
        editor.apply();

        //volvemos a la pantalla de login
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; go home
            Intent intent = new Intent(this, Menu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("idJugador", idJugador);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}