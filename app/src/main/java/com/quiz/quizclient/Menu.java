package com.quiz.quizclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quiz.quizclient.modelo.Mazo;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Menu extends AppCompatActivity {
    int idJugador;

    AdaptadorMazos adapter;
    RecyclerView recyclerView;
    List<Mazo> mazos;
    EditText inputtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setTitle("Menú Principal");

        //recibiendo valores del login idJugador
        idJugador = getIntent().getIntExtra("idJugador", -1);
        recyclerView = findViewById(R.id.rv_mazos);

        cargarDatos();

    }

    private void cargarDatos() {

        API api = Client.getClient().create(API.class);
        api.getMazosFrom(idJugador).enqueue(new Callback<List<Mazo>>() {
            @Override
            public void onResponse(Call<List<Mazo>> call, Response<List<Mazo>> response) {
                if (response.isSuccessful()) {
                    mazos = response.body();
                    //traza para el log, recorrer mazos en un for en prog.funcional.
                    mazos.forEach(mazo -> Log.println(Log.DEBUG, "ERROR", mazo.getNombre()));
                    //establecemos el adaptador y el layout
                    adapter = new AdaptadorMazos(mazos);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Mazo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater MI = getMenuInflater();
        MI.inflate(R.menu.tb_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.app_bar_profile:
                Intent i = new Intent(this, Perfil.class);
                i.putExtra("idJugador", idJugador);
                startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    public void add(View v) {

                inputtxt = new EditText(Menu.this);
                AlertDialog dialog = new AlertDialog.Builder(Menu.this)
                        .setTitle("Nuevo Mazo")
                        .setMessage("Titula tu nuevo mazo")
                        .setView(inputtxt)
                        .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                API api = Client.getClient().create(API.class);
                                Mazo mazo = new Mazo();
                                mazo.setNombre(inputtxt.getText().toString());
                                mazo.setIdJugador(idJugador);

                                Call<Mazo> call =  api.NewMazo(mazo);
                                call.enqueue(new Callback<Mazo>() {
                                    @Override
                                    public void onResponse(Call<Mazo> call, Response<Mazo> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "Mazo Creado "+response.code(), Toast.LENGTH_LONG).show();

                                        }else {
                                            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Mazo> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.show();
             }


}