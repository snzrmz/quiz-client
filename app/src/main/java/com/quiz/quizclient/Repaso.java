package com.quiz.quizclient;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizclient.modelo.Respuesta;
import com.quiz.quizclient.modelo.Tarjeta;
import com.quiz.quizclient.restclient.API;

import java.util.ArrayList;
import java.util.List;

public class Repaso extends AppCompatActivity {

    public interface OnRespuestaReponse { void tarjetas(List<Respuesta> respuestas); }

    int idJugador;
    String nombreMazo;
    TextView txtPregunta;
    TextView txtRespuesta;
    LinearLayout lLRespuestas;
    List<Tarjeta> clone_tarjetas;
    TextView txtNumTarjeta;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repaso);
        idJugador = getIntent().getIntExtra("idJugador", -1);
        nombreMazo = getIntent().getStringExtra("nombreMazo");
        getSupportActionBar().setTitle("Repasando " + nombreMazo);
        txtPregunta = findViewById(R.id.txtPregunta);
        lLRespuestas = findViewById(R.id.lLRespuestas);
        txtNumTarjeta = findViewById(R.id.txtNumTarjeta);
        txtRespuesta = findViewById(R.id.txtRespuesta);
        EditText etRespuesta = findViewById(R.id.etRespuesta);
        Button btnAceptar = findViewById(R.id.btnAceptar);
        List<String> respuestasUsuario = new ArrayList<>();
        //recibiendo valores de mazos
        idJugador = getIntent().getIntExtra("idJugador", -1);
        List<Tarjeta> tarjetas = (List<Tarjeta>) getIntent().getSerializableExtra("tarjetas");


        tarjetas.forEach(tarjeta -> Log.println(Log.DEBUG, "LOG", tarjeta.getPregunta()));

        //Falta sacar respuestas para tener el hilo de esta actividad "limpio" sin Callbacks

    }


}