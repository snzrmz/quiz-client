package com.quiz.quizclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;


public class CreaTarjeta extends AppCompatActivity {
    RadioButton multires, monores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_tarjeta);
    }

    public void add(View v){

    }
    public void info(View v){
        AlertDialog ayuda = new AlertDialog.Builder(this)
                .setTitle("Ayuda")
                .setMessage("Monorespuesta: El jugador tendrá que escribir la respuesta.")
                .setMessage("Multirespuesta: El jugador tendrá que elegir la respuesta entre varias opciones.")
                .setPositiveButton("Entendido",null)
                .create();
            ayuda.show();
    }
}