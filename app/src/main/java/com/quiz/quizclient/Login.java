package com.quiz.quizclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {


    //interfaz usada como evento de llamada asíncrona de RetroFit loginCorrecto()
    public interface OnLoginResponse {
        void respuesta(boolean esLoginCorrecto);
    }

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
    boolean nuevoUsuario;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //al venir de startActivityForResult se ejecutara lo siguiente
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                nuevoUsuario = data.getBooleanExtra("nuevoJugador", false);
                idJugador = data.getIntExtra("idJugador", -1);
                Log.d("LOG", "Resultado correcto, obtenido valores " + idJugador + " nuevo:" + nuevoUsuario);
                iniciarMenu();
            }
        } else {
            Log.d("LOG", "FALLO " + requestCode);

        }
    }


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
        //reconstruyendo actividad
        if (savedInstanceState != null) {
            esLoginCorrecto = savedInstanceState.getBoolean(STATE_LOGINSTATUS, false);
            email = savedInstanceState.getString(STATE_email);
            contrasena = savedInstanceState.getString(STATE_contrasena);

        }
        obtenerPreferencias();
        if (esLoginCorrecto) {
            iniciarMenu();
        }

        getSupportActionBar().setTitle("Inicio sesión");
        setContentView(R.layout.activity_login);
        botonLogin = findViewById(R.id.BTN_registrar);
        etContrasena = findViewById(R.id.password_text);
        etEmail = findViewById(R.id.txtNuevoMazo);
    }


    private void obtenerPreferencias() {
        preferencias = getSharedPreferences("IDvalue", 0);
        esLoginCorrecto = preferencias.getBoolean(STATE_LOGINSTATUS, false);
        idJugador = preferencias.getInt(STATE_idjugador, -1);
    }

    private void guardarPreferencias() {
        preferencias = getSharedPreferences("IDvalue", 0);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean(STATE_LOGINSTATUS, esLoginCorrecto);
        editor.putInt(STATE_idjugador, idJugador);
        editor.apply();
    }

    private boolean loginCorrecto(OnLoginResponse callback) {
        esLoginCorrecto = false;
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
                            guardarPreferencias();
                            callback.respuesta(esLoginCorrecto);
                        }
                    }
                }
                if (!esLoginCorrecto) {

                    Toast.makeText(getApplicationContext(), "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {

                Snackbar.make(null, "Error: "+t.getMessage() , Snackbar.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return esLoginCorrecto;
    }


    public void onComprobarLogin(View v) {

        email = etEmail.getText().toString();
        contrasena = etContrasena.getText().toString();
        loginCorrecto(esLoginCorrecto -> {
            iniciarMenu();
        });

    }

    private void iniciarMenu() {
        Intent intent = new Intent(this, Menu.class);
        //iniciando actividad
        intent.putExtra(STATE_idjugador, idJugador);
        startActivity(intent);
    }

    public void iniciarRegistro(View v){
        int LAUNCH_SECOND_ACTIVITY = 1;
        Intent intent = new Intent(this, Registro.class);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }
}
