package com.quiz.quizclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
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


public class CreaTarjeta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int idJugador, idTar;
    String mazo_selecionado;
    Spinner Spin;
    TextInputEditText pregunta, resp1, resp2, resp3, resp4, respuesta;
    CheckBox cbx1, cbx2, cbx3, cbx4;
    ImageView iV;


    boolean TipoMulti = false;

    public ArrayList<String> mazosSp = new ArrayList<String>(); // lista para alojar mazos en el spinner


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 2
                && resultCode == Activity.RESULT_OK) {
            // resultData contiene la uri del documento seleccionado
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                iV.setImageURI(uri);
                iV.setMaxHeight(600);
            } else {
                iV.setBackgroundResource(R.drawable.ic_baseline_add_a_photo_24);
                iV.setMaxHeight(300);
            }
        }
    }

    //Para que pueda compararse si los TextInputEditText están vacíos y desmarcar así los CheckBoxes
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            cbx1.setEnabled(!resp1.getText().toString().isEmpty());
            cbx2.setEnabled(!resp2.getText().toString().isEmpty());
            cbx3.setEnabled(!resp3.getText().toString().isEmpty());
            cbx4.setEnabled(!resp4.getText().toString().isEmpty());
            cbx1.setChecked(cbx1.isChecked() && !resp1.getText().toString().isEmpty());
            cbx2.setChecked(cbx2.isChecked() && !resp2.getText().toString().isEmpty());
            cbx3.setChecked(cbx3.isChecked() && !resp3.getText().toString().isEmpty());
            cbx4.setChecked(cbx4.isChecked() && !resp4.getText().toString().isEmpty());
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_tarjeta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nueva Tarjeta");
        idJugador = getIntent().getIntExtra("idJugador", -1);
        Spin = findViewById(R.id.spinnerMazos);
        iV = findViewById(R.id.imageView);
        pregunta = findViewById(R.id.pregunta_input);
        llenarSpinner();
    }


    public void addRespuestasMulti(View v) {
        TipoMulti = true;

        View view = this.getLayoutInflater().inflate(R.layout.layout_crea_respuesta_multiple, null);

        resp1 = view.findViewById(R.id.respuesta_input);
        resp2 = view.findViewById(R.id.respuesta2_input);
        resp3 = view.findViewById(R.id.respuesta3_input);
        resp4 = view.findViewById(R.id.respuesta4_input);

        cbx1 = view.findViewById(R.id.CB1);
        cbx2 = view.findViewById(R.id.CB2);
        cbx3 = view.findViewById(R.id.CB3);
        cbx4 = view.findViewById(R.id.CB4);

        //TextWatcher
        resp1.addTextChangedListener(watcher);
        resp2.addTextChangedListener(watcher);
        resp3.addTextChangedListener(watcher);
        resp4.addTextChangedListener(watcher);


        AlertDialog multires = new AlertDialog.Builder(this)
                .setTitle("Respuesta Múltiple")
                .setView(view)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        multires.show();

    }

    public void addRespuestasMono(View v) {
        TipoMulti = false;
        View view = this.getLayoutInflater().inflate(R.layout.layout_crea_respuesta_unica, null);
        respuesta = view.findViewById(R.id.respuesta_unica_input);

        AlertDialog monores = new AlertDialog.Builder(this)
                .setTitle("Respuesta Única")
                .setView(view)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        monores.show();

    }

    public void addTarjeta(View v) {


        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setPregunta(pregunta.getText().toString());
        tarjeta.setNombreMazo(mazo_selecionado);
        tarjeta.setIdJugador(idJugador);
        tarjeta.setRecursoRuta(null);//null de momento
        if (TipoMulti) {
            tarjeta.setTipoRespuesta("MULTIPLE");

        } else {
            tarjeta.setTipoRespuesta("UNICA");
        }

        persistirTarjeta(tarjeta);
    }

    private void persistirTarjeta(Tarjeta tarjeta) {
        API api = Client.getClient().create(API.class);
        Call<Tarjeta> call = api.createTarjeta(tarjeta);
        call.enqueue(new Callback<Tarjeta>() {
            @Override
            public void onResponse(Call<Tarjeta> call, Response<Tarjeta> response) {
                if (response.isSuccessful()) {
                    Snackbar.make(findViewById(R.id.btn_nuevatarjeta), "¡Tarjeta creada! ", Snackbar.LENGTH_LONG).show();
                    Log.d("LOG", String.valueOf(response.body()));
                } else {
                    Snackbar.make(findViewById(R.id.btn_nuevatarjeta), "Error " + response.code(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Tarjeta> call, Throwable t) {
                Snackbar.make(findViewById(R.id.BTN_registrar), "Error: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void info(View v) {
        AlertDialog ayuda = new AlertDialog.Builder(this)
                .setTitle("Ayuda")
                .setMessage("Monorespuesta: El jugador tendrá que escribir la respuesta.\n\nMultirespuesta: El jugador tendrá que elegir la respuesta entre varias opciones.")
                .setPositiveButton("Entendido", null)
                .create();
        ayuda.show();
    }

    private void llenarSpinner() {
        API api = Client.getClient().create(API.class);
        api.getMazosFrom(idJugador).enqueue(new Callback<List<Mazo>>() {
            @Override
            public void onResponse(Call<List<Mazo>> call, Response<List<Mazo>> response) {
                if (response.isSuccessful()) {
                    List<Mazo> mismazos = response.body();
                    for (Mazo mz : mismazos) {
                        mazosSp.add(mz.getNombre());
                    }
                    //despliega los mazos al hacer click en el spinner
                    Spin.setOnItemSelectedListener(CreaTarjeta.this);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreaTarjeta.this, android.R.layout.simple_list_item_1, mazosSp);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spin.setAdapter(adapter);
                } else {
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
        switch (adapterView.getId()) {
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

    public void elegirImagen(View v) {
        final int PICK_IMAGE = 2;
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }
}