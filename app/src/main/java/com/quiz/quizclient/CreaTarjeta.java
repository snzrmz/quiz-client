package com.quiz.quizclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.quiz.quizclient.modelo.Mazo;
import com.quiz.quizclient.modelo.Tarjeta;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreaTarjeta extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int idJugador;
    String respuestaU,respuesta1,respuesta2,respuesta3,respuesta4, mazo_selecionado;
    Spinner Spin;
    TextInputEditText pregunta;
    boolean TipoMulti = false;
    public ArrayList<String> mazosSp = new ArrayList<String>(); // lista para alojar mazos en el spinner


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_tarjeta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nueva Tarjeta");
        idJugador = getIntent().getIntExtra("idJugador", -1);
        Spin = findViewById(R.id.spinnerMazos);
        llenarSpinner();

    }



    public void addRespuestasMulti(View v){
        View view = this.getLayoutInflater().inflate(R.layout.layout_crea_respuesta_multiple, null);
        TextInputEditText resp1 = view.findViewById(R.id.respuesta_input);
        TextInputEditText resp2 = view.findViewById(R.id.respuesta2_input);
        TextInputEditText resp3 = view.findViewById(R.id.respuesta3_input);
        TextInputEditText resp4 = view.findViewById(R.id.respuesta4_input);
        TipoMulti = true;
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
        View view = this.getLayoutInflater().inflate(R.layout.layout_crea_respuesta_unica, null);
        TextInputEditText respuesta = view.findViewById(R.id.respuesta_unica_input);
        TipoMulti = false;

        AlertDialog monores = new AlertDialog.Builder(this)
                .setTitle("Respuesta Única")
                .setView(view)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        respuestaU = respuesta.getText().toString();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        monores.show();


    }

    public void addTarjeta(View v){
        API api = Client.getClient().create(API.class);

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setPregunta(pregunta.getText().toString());
        tarjeta.setNombreMazo(mazo_selecionado);
        tarjeta.setIdJugador(idJugador);
        tarjeta.setRecursoRuta(null);//null de momento
        if (TipoMulti=true) {
            tarjeta.setTipoRespuesta("MULTIPLE");
        }else{
            tarjeta.setTipoRespuesta("UNICA");
        }

        Call<Void> call = api.newTarjeta(tarjeta);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "¡Tarjeta Creada!", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Error creando la tarjeta " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.println(Log.DEBUG, "LOG", t.getMessage());
            }
        });

    }
    public void info(View v){
        AlertDialog ayuda = new AlertDialog.Builder(this)
                .setTitle("Ayuda")
                .setMessage("Monorespuesta: El jugador tendrá que escribir la respuesta.\n\nMultirespuesta: El jugador tendrá que elegir la respuesta entre varias opciones.")
                .setPositiveButton("Entendido",null)
                .create();
            ayuda.show();
    }

    private void llenarSpinner(){
        API api = Client.getClient().create(API.class);
        api.getMazosFrom(idJugador).enqueue(new Callback<List<Mazo>>() {
            @Override
            public void onResponse(Call<List<Mazo>> call, Response<List<Mazo>> response) {
                if (response.isSuccessful()) {
                    List<Mazo> mismazos = response.body();
                    for (Mazo mz: mismazos ){
                        mazosSp.add(mz.getNombre().toString());
                    }
                    //despliega los mazos al hacer click en el spinner
                    Spin.setOnItemSelectedListener(CreaTarjeta.this);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreaTarjeta.this, android.R.layout.simple_list_item_1, mazosSp);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spin.setAdapter(adapter);
                }else{
                    Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mazo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId())
        {
            case R.id.spinnerMazos:
                mazo_selecionado = adapterView.getSelectedItem().toString(); //se queda el mazo selecionado
                break;

            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //no hacer nada
    }
}