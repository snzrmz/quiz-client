package com.quiz.quizclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.quiz.quizclient.modelo.Mazo;
import com.quiz.quizclient.modelo.Tarjeta;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreaTarjeta extends AppCompatActivity {
    int idJugador;
    RadioButton multires, monores;
    String respuesta1,respuesta2,respuesta3,respuesta4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_tarjeta);
        idJugador = getIntent().getIntExtra("idJugador", -1);
    }

    public void addRespuestasMulti(View v){
        View view = this.getLayoutInflater().inflate(R.layout.layout_crea_respuesta_multiple, null);
        TextInputEditText resp1 = view.findViewById(R.id.respuesta_input);
        TextInputEditText resp2 = view.findViewById(R.id.respuesta2_input);
        TextInputEditText resp3 = view.findViewById(R.id.respuesta3_input);
        TextInputEditText resp4 = view.findViewById(R.id.respuesta4_input);

        AlertDialog multires = new AlertDialog.Builder(this)
                .setTitle("Respuesta Múltiple")
                .setView(view)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        respuesta1 = resp1.getText().toString();
                        respuesta2 = resp2.getText().toString();
                        respuesta3 = resp3.getText().toString();
                        respuesta4 = resp4.getText().toString();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        multires.show();

    }
    public void addRespuestasMono(View v){
        View view = this.getLayoutInflater().inflate(R.layout.layout_crea_respuesta_multiple, null);
        TextInputEditText resp1 = view.findViewById(R.id.respuesta_unica_input);


        AlertDialog monores = new AlertDialog.Builder(this)
                .setTitle("Respuesta Múltiple")
                .setView(view)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        respuesta1 = resp1.getText().toString();

                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        monores.show();

    }
   /* public void addTarjeta(View v){
        API api = Client.getClient().create(API.class);
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setIdJugador();
        tarjeta.
    }*/
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