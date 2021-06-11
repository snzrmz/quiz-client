package com.quiz.quizclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {
    //evento que se lanza al obtener jugador de Retrofit
    public interface OnJugadorConseguido {

        void establecerDatos(Jugador jugador);
    }

    private final static String STATE_LOGINSTATUS = "esLoginCorrecto";
    private final static String STATE_IDJUGADOR = "idJugador";
    TextView jugador_usuario, jugador_fechaCreacion, jugador_email;
    int idJugador;
    SharedPreferences preferencias;
    API api;
    OnJugadorConseguido onJugadorConseguido;
    Jugador jugador;
    String ip, puerto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setTitle("Mi Perfil");
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.orange1));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idJugador = getIntent().getIntExtra(STATE_IDJUGADOR, -1);
        ip = getIntent().getStringExtra("ip");
        puerto = getIntent().getStringExtra("puerto");
        Log.d("LOG", "ip: " + ip + " puerto: " + puerto);
        jugador_usuario = findViewById(R.id.txtUsuario);
        jugador_fechaCreacion = findViewById(R.id.txtFechaCreacion);
        jugador_email = findViewById(R.id.txtEmail);

        onJugadorConseguido = new OnJugadorConseguido() {
            @Override
            public void establecerDatos(Jugador jugador) {
                if (jugador == null) {
                    Log.d("LOG", "jugador null, tratar condición");
                    return;
                }
                jugador_usuario.setText(jugador.getUsuario());
                Locale locale = new Locale("es", "ES");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMMM 'de' yyyy");
                jugador_fechaCreacion.setText("Miembro desde " + LocalDate.parse(jugador.getFechaCreacion(), formatter).format(formatter2));
                jugador_email.setText(jugador.getEmail());
            }
        };
        getJugador(idJugador, onJugadorConseguido);

    }

    private void getJugador(int idJugador, OnJugadorConseguido callback) {
        api = Client.getClient(ip, puerto).create(API.class);
        Call<Jugador> call = api.getJugadorById(idJugador);
        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                jugador = response.body();
                //callback que será ejecutado al recibir el jugador
                callback.establecerDatos(jugador);
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
                TextInputEditText newuser = view.findViewById(R.id.user_refactor);
                TextInputEditText newemail = view.findViewById(R.id.email_refactor);
                newuser.setText(jugador.getUsuario());
                newemail.setText(jugador.getEmail());
                //  newuser.setText(jugador.getEmail());
                AlertDialog dialog = new AlertDialog.Builder(Perfil.this)
                        .setTitle("Editar mis datos")
                        .setView(view)
                        .setPositiveButton("Guardar cambios", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                api = Client.getClient(ip, puerto).create(API.class);
                                jugador.setUsuario(newuser.getText().toString());
                                jugador.setEmail(newemail.getText().toString());
                                Call<Void> call = api.updateJugador(jugador);
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            //reutilizamos el callback para actualizar el perfil
                                            onJugadorConseguido.establecerDatos(jugador);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.d("LOG", t.getMessage());
                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00B300"));
                    }
                });
                dialog.show();
                break;
            case android.R.id.home: //necesario para que al pulsar la flecha de volver no se resetee la actividad
                finish();
                return true;
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