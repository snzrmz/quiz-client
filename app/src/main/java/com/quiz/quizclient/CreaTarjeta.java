package com.quiz.quizclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.quiz.quizclient.modelo.Mazo;
import com.quiz.quizclient.modelo.Respuesta;
import com.quiz.quizclient.modelo.Tarjeta;
import com.quiz.quizclient.modelo.Tarjeta_Respuesta_Multiple;
import com.quiz.quizclient.modelo.Tarjeta_Respuesta_Unica;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    Uri uri;
    String ip, puerto;


    boolean TipoMulti = false;

    public ArrayList<String> mazosSp = new ArrayList<String>(); // lista para alojar mazos en el spinner

    public interface OnTarjetaPersistida {
        void respuesta(int idTarjeta);
    }


    public interface OnImagenPersistida {
        void respuesta(String recursoRuta);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 2
                && resultCode == Activity.RESULT_OK) {
            // resultData contiene la uri del documento seleccionado
            uri = null;
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
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.orange1));
        idJugador = getIntent().getIntExtra("idJugador", -1);
        ip = getIntent().getStringExtra("ip");
        puerto = getIntent().getStringExtra("puerto");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
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
        if (pregunta.getText().toString().equals("") || !TipoMulti && respuesta == null) {
            Snackbar.make(findViewById(R.id.btn_nuevatarjeta), "Rellene los datos obligatorios", Snackbar.LENGTH_LONG).show();
            return;
        }
        Tarjeta tarjeta = new Tarjeta();
        if (TipoMulti) {
            tarjeta.setTipoRespuesta("MULTIPLE");

            if (resp1.getText().toString().equals("") &&
                    resp2.getText().toString().equals("") &&
                    resp3.getText().toString().equals("") &&
                    resp4.getText().toString().equals("")) {

                Snackbar.make(findViewById(R.id.btn_nuevatarjeta), "No ha introducido respuestas", Snackbar.LENGTH_LONG).show();
                return;
            }
        } else {
            tarjeta.setTipoRespuesta("UNICA");
            if (respuesta.getText().toString().equals("")) {
                Snackbar.make(findViewById(R.id.btn_nuevatarjeta), "Introduce una respuesta", Snackbar.LENGTH_LONG).show();
                return;
            }
        }
        tarjeta.setPregunta(pregunta.getText().toString());
        tarjeta.setNombreMazo(mazo_selecionado);
        tarjeta.setIdJugador(idJugador);
        OnTarjetaPersistida onTarjetaPersistida = new OnTarjetaPersistida() {
            @Override
            public void respuesta(int idTarjeta) {
                if (TipoMulti) {
                    OnTarjetaPersistida onTarjetaRespuestaMultiple = new OnTarjetaPersistida() {
                        @Override
                        public void respuesta(int idTarjeta) {
                            //persistir tabla Respuesta (respuestas multiples, si es correcta, etc)
                            List<Respuesta> respuestas = new ArrayList<>();
                            Respuesta respuesta = null;
                            if (!resp1.getText().toString().equals("")) {
                                respuesta = new Respuesta();
                                respuesta.setIdTarjeta(idTarjeta);
                                respuesta.setValor(resp1.getText().toString());
                                respuesta.setCorrecta(cbx1.isChecked() ? 1 : 0);
                                respuestas.add(respuesta);
                            }
                            if (!resp2.getText().toString().equals("")) {
                                respuesta = new Respuesta();
                                respuesta.setIdTarjeta(idTarjeta);
                                respuesta.setValor(resp2.getText().toString());
                                respuesta.setCorrecta(cbx2.isChecked() ? 1 : 0);
                                respuestas.add(respuesta);
                            }
                            if (!resp3.getText().toString().equals("")) {
                                respuesta = new Respuesta();
                                respuesta.setIdTarjeta(idTarjeta);
                                respuesta.setValor(resp3.getText().toString());
                                respuesta.setCorrecta(cbx3.isChecked() ? 1 : 0);
                                respuestas.add(respuesta);
                            }
                            if (!resp4.getText().toString().equals("")) {
                                respuesta = new Respuesta();
                                respuesta.setIdTarjeta(idTarjeta);
                                respuesta.setValor(resp4.getText().toString());
                                respuesta.setCorrecta(cbx4.isChecked() ? 1 : 0);
                                respuestas.add(respuesta);
                            }
                            persistirRespuestas(respuestas);
                        }
                    };
                    persistirTarjetaRespuestaMulitple(idTarjeta, onTarjetaRespuestaMultiple);
                } else {
                    persistirTarjetaRespuestaUnica(idTarjeta);
                }
            }
        };
        OnImagenPersistida onImagenPersistida = new OnImagenPersistida() {

            @Override
            public void respuesta(String recursoRuta) {
                if (recursoRuta.equals("")) {
                    tarjeta.setRecursoRuta(null);
                    return;
                }
                Log.d("LOG", "recurso ruta establecido " + recursoRuta);
                tarjeta.setRecursoRuta(recursoRuta);
                persistirTarjeta(tarjeta, onTarjetaPersistida);
            }
        };

        if (uri != null) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                subirImagen(bitmap, onImagenPersistida);
            } catch (IOException e) {
            }
        } else {
            //Si es null el usuario o no ha establecido imagen o no era válida
            //pero se ha de guardar la tajeta
            Log.d("LOG", "Uri null");
            persistirTarjeta(tarjeta, onTarjetaPersistida);
        }

    }

    private void subirImagen(Bitmap bitmap, OnImagenPersistida callback) {
        Log.d("LOG", "subiendo imagen...");
        String filename = generateRandomString() + ".jpg";
        //https://stackoverflow.com/a/45832835
        //create a file to write bitmap data
        File f = new File(getApplicationContext().getCacheDir(), filename);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("LOG", e.getMessage());
        }
        //Convert bitmap to byte array

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            Log.d("LOG", e.getMessage());
        }
        try {
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("LOG", e.getMessage());
        }
        RequestBody requestbody = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), f);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", f.getName(), requestbody);
        API api = Client.getClient(ip, puerto).create(API.class);
        Call<ResponseBody> call = api.uploadImage(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    Log.d("LOG", "foto subida: " + filename);
                    callback.respuesta(filename);
                } else {
                    Log.d("LOG", String.valueOf(response.raw()));
                    callback.respuesta("");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("LOG", "Error al subir imagen " + t.getMessage());
            }
        });
    }

    private void metodoimportante() {
        //Guardando tarjeta


    }

    private String generateRandomString() {
        //baeldung.com/java-random-string
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    //Tabla Respuesta
    private void persistirRespuestas(List<Respuesta> respuestas) {
        Log.d("LOG", "persistiendo respuestas: " + respuestas);
        API api = Client.getClient(ip, puerto).create(API.class);
        Call<Void> call = api.createRespuesta(respuestas);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("LOG", "Tarjeta de respuesta múltiple creada");
                    Snackbar.make(findViewById(R.id.btn_nuevatarjeta), "¡Tarjeta creada! ", Snackbar.LENGTH_LONG).show();
                    ArrayList<TextInputEditText> cajas = new ArrayList<>();
                    restablecerTrasPersistir(Arrays.asList(new TextInputEditText[]{pregunta, resp1, resp2, resp3, resp4}));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("LOG", "Tarjeta de respuesta múltiple falló " + t.getMessage());
            }
        });
    }


    private void persistirTarjetaRespuestaMulitple(int idTarjeta, OnTarjetaPersistida callback) {
        Tarjeta_Respuesta_Multiple trm = new Tarjeta_Respuesta_Multiple();
        trm.setIdTarjeta(idTarjeta);
        Log.d("LOG", String.valueOf(trm.getIdTarjeta()));
        API api = Client.getClient(ip, puerto).create(API.class);
        Call<Void> call = api.createRespuestaTarjetaMultiple(trm);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("LOG", "idTarjeta de trm " + idTarjeta);
                callback.respuesta(idTarjeta);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    private void persistirTarjetaRespuestaUnica(int idTarjeta) {
        Tarjeta_Respuesta_Unica tru = new Tarjeta_Respuesta_Unica();
        tru.setIdTarjeta(idTarjeta);
        tru.setValor(Objects.requireNonNull(respuesta.getText()).toString());
        Log.d("LOG", tru.getValor() + " " + tru.getIdTarjeta());
        API api = Client.getClient(ip, puerto).create(API.class);
        Call<Void> call = api.createRespuestaOfTarjetaUnica(tru);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Snackbar.make(findViewById(R.id.btn_nuevatarjeta), "¡Tarjeta creada! ", Snackbar.LENGTH_LONG).show();
                    List<TextInputEditText> cajas = new ArrayList<>();
                    restablecerTrasPersistir(Arrays.asList(new TextInputEditText[]{pregunta, respuesta}));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    //Una vez que se ha creado una tarjeta limpiar los datos para no crear datos corrompidos posteriormente
    private void restablecerTrasPersistir(List<TextInputEditText> cajas) {
        //limpiar cajas de texto
        cajas.forEach(caja -> {
            if (caja != null) caja.getText().clear();
        });
        //restablecer otros datos
        iV.setMaxHeight(300);
        iV.setImageURI(null);
        iV.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
        uri = null;
    }

    private void persistirTarjeta(Tarjeta tarjeta, OnTarjetaPersistida callback) {
        API api = Client.getClient(ip, puerto).create(API.class);
        Call<Void> call = api.createTarjeta(tarjeta);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String[] location = response.headers().get("Location").split("/");
                    int idTarjeta = Integer.parseInt(location[location.length - 1]);
                    Log.d("LOG", "Tarjeta creada, procediendo a persisitir respuesta/s");
                    callback.respuesta(idTarjeta);
                } else {
                    Snackbar.make(findViewById(R.id.btn_nuevatarjeta), "Error " + response.code(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Snackbar.make(findViewById(R.id.btn_nuevatarjeta), "Error: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
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
        API api = Client.getClient(ip, puerto).create(API.class);
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