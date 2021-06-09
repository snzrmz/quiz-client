package com.quiz.quizclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizclient.modelo.Repaso;
import com.quiz.quizclient.modelo.Tarjeta;
import com.quiz.quizclient.modelo.Tarjeta_Repaso_Acertado;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerRepasos extends AppCompatActivity {

    int idJugador;
    String nombreMazo;
    List<Repaso> repasos;
    RecyclerView recyclerView;
    AdaptadorRepasos adaptadorRepasos;
    boolean cambiado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_repasos);
        idJugador = getIntent().getIntExtra("idJugador", -1);
        nombreMazo = getIntent().getStringExtra("nombreMazo");
        getSupportActionBar().setTitle("Repasos de " + nombreMazo);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.orange1));
        repasos = (List<Repaso>) getIntent().getSerializableExtra("repasos");

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
        adaptadorRepasos = new AdaptadorRepasos(getApplicationContext(), repasos, null);
        recyclerView.setAdapter(adaptadorRepasos);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        API api = Client.getClient().create(API.class);
                        Call<List<Tarjeta>> call = api.getFromMazo(idJugador, nombreMazo);
                        call.enqueue(new Callback<List<Tarjeta>>() {
                            @Override
                            public void onResponse(Call<List<Tarjeta>> call, Response<List<Tarjeta>> response) {
                                if (response.isSuccessful()) {
                                    List<Tarjeta> tarjetas = response.body();
                                    //al traernos las tarjetas podremos comprobarlas con los repasos
                                    //y establecer las que son correctas
                                    List<Tarjeta_Repaso_Acertado> tarjetasAcertadas = repasos.get(position).getTarjetaRepasoAcertado();
                                    for (int i = 0; i < Objects.requireNonNull(tarjetas).size(); i++) {
                                        for (int j = 0; j < tarjetasAcertadas.size(); j++) {
                                            if (tarjetas.get(i).getIdTarjeta() == tarjetasAcertadas.get(j).getTarjeta_idTarjeta()) {
                                                tarjetas.get(i).setCorrecta(true);
                                                break;
                                            }
                                        }
                                    }

                                 /*   AdaptadorTarjetas adaptadorTarjetas = new AdaptadorTarjetas(getApplicationContext(), tarjetas, true);
                                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false));
                                    recyclerView.setAdapter(adaptadorTarjetas);*/
                                    Intent intent = new Intent(getBaseContext(), VerTarjetas.class);
                                    intent.putExtra("tarjetas", (Serializable) tarjetas);
                                    intent.putExtra("idJugador", idJugador);
                                    intent.putExtra("nombreMazo", nombreMazo);
                                    intent.putExtra("verResultados", true);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Tarjeta>> call, Throwable t) {
                                Toast.makeText(getBaseContext(), "Error al recuperar tarjetas", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }
}