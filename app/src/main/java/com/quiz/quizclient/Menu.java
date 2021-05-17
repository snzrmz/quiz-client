package com.quiz.quizclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setTitle("Menú Principal");

        //recibiendo valores del login idJugador
        idJugador = getIntent().getIntExtra("idJugador", -1);
        recyclerView = findViewById(R.id.rv);
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
                //no se envia putExtra de idJugador ya que se recoge por sharedPreferences
                startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}