package com.quiz.quizclient;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizclient.modelo.Tarjeta;

import java.util.List;

public class VerTarjetas extends AppCompatActivity {
    int idJugador;
    List<Tarjeta> tarjetas;
    RecyclerView recyclerView;
    String nombreMazo;
    AdaptadorTarjetas adaptadorTarjetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tarjetas);
        //recibiendo valores
        idJugador = getIntent().getIntExtra("idJugador", -1);
        nombreMazo = getIntent().getStringExtra("nombreMazo");
        tarjetas = (List<Tarjeta>) getIntent().getSerializableExtra("tarjetas");
        getSupportActionBar().setTitle("Tarjetas de " + nombreMazo);
        //establecemos el recycler y su adaptador
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false));
        adaptadorTarjetas = new AdaptadorTarjetas(getApplicationContext(), tarjetas, false);
        recyclerView.setAdapter(adaptadorTarjetas);
    }
}