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

import com.google.android.material.textfield.TextInputEditText;
import com.quiz.quizclient.modelo.Mazo;
import com.quiz.quizclient.modelo.Respuesta;
import com.quiz.quizclient.modelo.Tarjeta;
import com.quiz.quizclient.modelo.Tarjeta_Respuesta_Multiple;
import com.quiz.quizclient.modelo.Tarjeta_Respuesta_Unica;
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
        View view = this.getLayoutInflater().inflate(R.layout.layout_crea_respuesta_unica, null);
        respuesta = view.findViewById(R.id.respuesta_unica_input);
        TipoMulti = false;

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
        boolean esMultiple = false;

        API api = Client.getClient().create(API.class);

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setPregunta(pregunta.getText().toString());
        tarjeta.setNombreMazo(mazo_selecionado);
        tarjeta.setIdJugador(idJugador);
        tarjeta.setRecursoRuta(null);//null de momento
        if (TipoMulti = true) {
            tarjeta.setTipoRespuesta("MULTIPLE");
            esMultiple = true;
        } else {
            tarjeta.setTipoRespuesta("UNICA");
        }

        Call<Tarjeta> call = api.createTarjeta(tarjeta);
        call.enqueue(new Callback<Tarjeta>() {
            @Override
            public void onResponse(Call<Tarjeta> call, Response<Tarjeta> response) {
                if (response.isSuccessful()) {
                    idTar = response.body().getIdTarjeta();
                    Toast.makeText(getApplicationContext(), "¡Tarjeta Creada!", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Error creando la tarjeta " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Tarjeta> call, Throwable t) {
                Log.println(Log.DEBUG, "LOG", t.getMessage());
            }
        });


        if (!esMultiple) {
            Respuesta res = new Respuesta();
            res.setCorrecta(1);
            res.setValor(respuesta.getText().toString());
            res.setIdTarjeta(idTar);
            Call<Respuesta> callres = api.createRespuesta(res);
            callres.enqueue(new Callback<Respuesta>() {
                @Override
                public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                    Toast.makeText(getBaseContext(), "¡Respuesta guardada!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Respuesta> call, Throwable t) {

                }
            });

            Tarjeta_Respuesta_Unica tju = new Tarjeta_Respuesta_Unica(); //crea la tarjeta respuesta unica
            tju.setIdTarjeta(idTar);
            tju.setValor(respuesta.getText().toString());
            Call<Tarjeta_Respuesta_Unica> callTJU = api.createTJU(tju);
            callTJU.enqueue(new Callback<Tarjeta_Respuesta_Unica>() {
                @Override
                public void onResponse(Call<Tarjeta_Respuesta_Unica> call, Response<Tarjeta_Respuesta_Unica> response) {

                }

                @Override
                public void onFailure(Call<Tarjeta_Respuesta_Unica> call, Throwable t) {

                }
            });

        } else {
            Tarjeta_Respuesta_Multiple tjm = new Tarjeta_Respuesta_Multiple(); // crea la tarjeta respuesta multiple
            tjm.setIdTarjeta(idTar);
            Call<Tarjeta_Respuesta_Multiple> callTJM = api.createTJM(tjm);
            callTJM.enqueue(new Callback<Tarjeta_Respuesta_Multiple>() {
                @Override
                public void onResponse(Call<Tarjeta_Respuesta_Multiple> call, Response<Tarjeta_Respuesta_Multiple> response) {

                }

                @Override
                public void onFailure(Call<Tarjeta_Respuesta_Multiple> call, Throwable t) {

                }
            });

            if (cbx1.isEnabled()) {    //si los checkbox estan activados(no confundir con checkeados) entonces crea la respuesta
                Respuesta res = new Respuesta();
                res.setValor(resp1.getText().toString());
                res.setIdTarjeta(idTar);
                if (cbx1.isChecked()) {
                    res.setCorrecta(1);
                } else {
                    res.setCorrecta(0);
                }
                Call<Respuesta> callres = api.createRespuesta(res);
                callres.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        Toast.makeText(getBaseContext(), "¡Respuesta guardada!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {

                    }
                });

            } else {
                return;
            }

            if (cbx2.isEnabled()) {
                Respuesta res = new Respuesta();
                res.setValor(resp2.getText().toString());
                res.setIdTarjeta(idTar);
                if (cbx2.isChecked()) {
                    res.setCorrecta(1);
                } else {
                    res.setCorrecta(0);
                }
                Call<Respuesta> callres = api.createRespuesta(res);
                callres.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        Toast.makeText(getBaseContext(), "¡Respuesta guardada!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {

                    }
                });
            } else {
                return;
            }
            if (cbx3.isEnabled()) {
                Respuesta res = new Respuesta();
                res.setValor(resp3.getText().toString());
                res.setIdTarjeta(idTar);
                if (cbx3.isChecked()) {
                    res.setCorrecta(1);
                } else {
                    res.setCorrecta(0);
                }
                Call<Respuesta> callres = api.createRespuesta(res);
                callres.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        Toast.makeText(getBaseContext(), "¡Respuesta guardada!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {

                    }
                });
            } else {
                return;
            }
            if (cbx4.isEnabled()) {
                Respuesta res = new Respuesta();
                res.setValor(resp4.getText().toString());
                res.setIdTarjeta(idTar);
                if (cbx4.isChecked()) {
                    res.setCorrecta(1);
                } else {
                    res.setCorrecta(0);
                }
                Call<Respuesta> callres = api.createRespuesta(res);
                callres.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

                    }

                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {

                    }
                });
            } else {
                return;
            }
        }


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