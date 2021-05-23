package com.quiz.quizclient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizclient.modelo.TarjetasConRespuestas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repaso extends AppCompatActivity {

    String nombreMazo;
    TextView txtPregunta, txtRespuesta, txtNumTarjeta;
    LinearLayout lLRespuestas;

    int idJugador, indiceTarjetaActual, numTarjetas, contadorCorrectas;
    EditText etRespuesta;
    Map<Integer, List<TarjetasConRespuestas>> mapTarjetasRespuestas;
    boolean esMultiple;
    List<Integer> claves;


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
        etRespuesta = findViewById(R.id.etRespuesta);
        Button btnAceptar = findViewById(R.id.btnAceptar);
        List<String> respuestasUsuario = new ArrayList<>();
        //recibiendo valores de mazos
        idJugador = getIntent().getIntExtra("idJugador", -1);
        List<TarjetasConRespuestas> tarjetasConRespuestas = (List<TarjetasConRespuestas>) getIntent().getSerializableExtra("tarjetasConRespuestas");

        //HashMap para deshacernos de repetidos
        mapTarjetasRespuestas = new HashMap<>();
        /*Shortcut for adding to List in a HashMap: https://stackoverflow.com/a/3019388*/
        for (TarjetasConRespuestas tcr : tarjetasConRespuestas) {
            mapTarjetasRespuestas.computeIfAbsent(tcr.getIdTarjeta(), k -> new ArrayList<>()).add(tcr);
        }

        //Guardamos las claves
        claves = new ArrayList<>(mapTarjetasRespuestas.keySet());

        //Establecemos valores
        numTarjetas = mapTarjetasRespuestas.size();
        indiceTarjetaActual = 0;
        contadorCorrectas = 0;
        txtNumTarjeta.setText(indiceTarjetaActual + 1 + "/" + numTarjetas);

        //segun sea unica o multiple se estableceran las vistas acordemente
        if (mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual)).get(0).getTipoRespuesta().equals("UNICA")) {
            establecerTarjetaSimple(mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual)).get(0));
        } else {
            establecerTarjetaMultiple(mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual)));
        }

    }

    void establecerTarjetaSimple(TarjetasConRespuestas tjr) {
        esMultiple = false;
        txtPregunta.setText(tjr.getPregunta());
        lLRespuestas.setOrientation(LinearLayout.HORIZONTAL);
        //Si se han eliminado todas las vistas agregar la necesaria
        if (lLRespuestas.getChildCount() == 0) {
            lLRespuestas.addView(etRespuesta);
        }
    }

    void establecerTarjetaMultiple(List<TarjetasConRespuestas> ltjr) {
        esMultiple = true;
        txtPregunta.setText(ltjr.get(0).getPregunta());
        lLRespuestas.removeAllViews();
        lLRespuestas.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < ltjr.size(); i++) {

            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setText(ltjr.get(i).getValor());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }

                // Your code to be executed on click
            });

            lLRespuestas.addView(checkBox);
        }
    }

    public void onAceptar(View v) {
        if (esMultiple) {

        } else {
            Log.println(Log.DEBUG, "LOG", mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual)).get(0).getValor());
            if (etRespuesta.getText().toString().equals(mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual)).get(0).getValor())) {
                Log.println(Log.DEBUG, "LOG", "Respueta correcta");
                contadorCorrectas++;
            }
        }
        indiceTarjetaActual++;
        txtNumTarjeta.setText(txtNumTarjeta.getText().toString().replaceFirst("\\d+", String.valueOf(indiceTarjetaActual + 1)));
        List<TarjetasConRespuestas> keySiguientePregunta = mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual));
        etRespuesta.setText("");
        if (keySiguientePregunta.get(0).getTipoRespuesta().equals("UNICA")) {
            establecerTarjetaSimple(keySiguientePregunta.get(0));
        } else {
            establecerTarjetaMultiple(keySiguientePregunta);
        }
    }

}

