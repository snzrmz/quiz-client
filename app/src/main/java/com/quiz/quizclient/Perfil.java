package com.quiz.quizclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {
    private final static String STATE_LOGINSTATUS = "esLoginCorrecto";
    private final static String STATE_IDJUGADOR = "idJugador";

    TextView jugador_usuario, jugador_fechaCreacion,jugador_email;
    String usuario, fecha, email, contrasena;
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
                contrasena = response.body().getPassword();
                jugador_usuario.setText(usuario);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEE d MMMM yyyy");
                jugador_fechaCreacion.setText("Miembro desde "+ LocalDate.parse(fecha, formatter).format(formatter2));
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
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater MI = getMenuInflater();
        MI.inflate(R.menu.tb_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.app_bar_edit_player:
                View view = Perfil.this.getLayoutInflater().inflate(R.layout.layout_actualizar_jug, null);
                TextInputEditText newuser = findViewById(R.id.user_refactor);
                TextInputEditText newemail = findViewById(R.id.email_refactor);
                AlertDialog dialog = new AlertDialog.Builder(Perfil.this)
                        .setTitle("Editar mis datos")
                        .setView(view)
                        .setPositiveButton("Guardar cambios", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                api = Client.getClient().create(API.class);

                                Jugador jugador = new Jugador();
                                jugador.setIdJugador(idJugador);
                                jugador.setPassword(contrasena);
                                if (newuser.getText().toString().isEmpty()){
                                    jugador.setUsuario(usuario);
                                }else{
                                    jugador.setUsuario(newuser.toString());
                                }
                                if (newemail.getText().toString().isEmpty()){
                                    jugador.setUsuario(email);
                                }else{
                                    jugador.setUsuario(newemail.toString());
                                }

                                Call<Void> call = api.updateJugador(jugador);
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.show();



        }
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