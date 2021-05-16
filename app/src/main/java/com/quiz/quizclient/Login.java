package com.quiz.quizclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private static final String STATE_LOGINSTATUS = "esLoginCorrecto";
    private static final String STATE_email = "email";
    private static final String STATE_contrasena = "contrasena";
    private static final String STATE_idjugador = "idJugador";

    public static boolean esLoginCorrecto;
    private int idJugador;
    private SharedPreferences preferencias;


    EditText etEmail, etContrasena;

    String email, contrasena;
    Button botonLogin;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //se ejecutara en el ciclo de vida antes de guardar una instancia
        outState.putBoolean(STATE_LOGINSTATUS, esLoginCorrecto);
        outState.putString(STATE_email, email);
        outState.putString(STATE_contrasena, contrasena);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Inicio sesión");
        setContentView(R.layout.activity_login);
        //reconstruyendo actividad
        if (savedInstanceState != null) {
            esLoginCorrecto = savedInstanceState.getBoolean(STATE_LOGINSTATUS, false);
            etEmail.setText(savedInstanceState.getString(STATE_email));
            etContrasena.setText(savedInstanceState.getInt(STATE_contrasena));

        }
        botonLogin = findViewById(R.id.BTN_registrar);
        etContrasena = findViewById(R.id.password_edit_text);
        etEmail = findViewById(R.id.Email_input);
        obtenerPreferencias();
        Log.println(Log.DEBUG, "DEBUG_STATE_CREATE", "login:" + esLoginCorrecto +
                " id: " + idJugador + " email: " + email + " contraseña " + contrasena);
        esJugador();

    }


    private void obtenerPreferencias() {
        preferencias = getPreferences(MODE_PRIVATE);
        esLoginCorrecto = preferencias.getBoolean(STATE_LOGINSTATUS, false);
        idJugador = preferencias.getInt(STATE_idjugador, -1);
    }

    private void guardarPreferencias() {
        preferencias = getSharedPreferences("IDvalue", 0);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean(STATE_LOGINSTATUS, esLoginCorrecto);
        editor.putInt(STATE_idjugador, idJugador);
        editor.putString(STATE_email, email);
        editor.putString(STATE_contrasena, contrasena);
        editor.apply();
    }

    private boolean loginCorrecto() {
        API api = Client.getClient().create(API.class);
        Call<Jugador> call = api.getJugadorByEmail(email);

        //si email y contrasena es igual a los proporcionados return true
        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                if (response.isSuccessful()) {
                    if (response.body().getEmail().equalsIgnoreCase(email)) {
                        if (response.body().getPassword().equals(contrasena)) {
                            esLoginCorrecto = true;
                            idJugador = response.body().getIdJugador();
                            Toast.makeText(getApplicationContext(), "Login correcto " + idJugador, Toast.LENGTH_SHORT).show();
                            //guardamos los valores necesarios del login
                            guardarPreferencias();
                            return;
                        }
                    }
                }
                esLoginCorrecto = false;
                Toast.makeText(getApplicationContext(), "Login incorrecto ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return esLoginCorrecto;
    }


    public void onComprobarLogin(View v) {
        email = etEmail.getText().toString();
        contrasena = etContrasena.getText().toString();
        if (loginCorrecto()) {
            esJugador();
        }
    }

    private void esJugador() {
        //recomprobamos que hay id correcto
        if (esLoginCorrecto && idJugador != -1) {
            Intent intent = new Intent(this, Menu.class);
            //iniciando actividad
            intent.putExtra(STATE_idjugador, idJugador);
            startActivity(intent);
        }
    }
}
