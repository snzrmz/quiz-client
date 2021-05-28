package com.quiz.quizclient;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizclient.modelo.Repaso;

import java.util.List;

public class VerRepasos extends AppCompatActivity {

    int idJugador;
    String nombreMazo;
    List<Repaso> repasos;
    RecyclerView recyclerView;
    AdaptadorRepasos adaptadorRepasos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_repasos);
        idJugador = getIntent().getIntExtra("idJugador", -1);
        nombreMazo = getIntent().getStringExtra("nombreMazo");
        getSupportActionBar().setTitle("Repasos de " + nombreMazo);
        repasos = (List<Repaso>) getIntent().getSerializableExtra("repasos");

        Log.d("LOG", idJugador + " " + nombreMazo);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
        adaptadorRepasos = new AdaptadorRepasos(getApplicationContext(), repasos, null);
        recyclerView.setAdapter(adaptadorRepasos);
    }
}