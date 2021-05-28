package com.quiz.quizclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.quiz.quizclient.modelo.Mazo;
import com.quiz.quizclient.modelo.Repaso;
import com.quiz.quizclient.modelo.Tarjeta;
import com.quiz.quizclient.modelo.TarjetasConRespuestas;
import com.quiz.quizclient.restclient.API;
import com.quiz.quizclient.restclient.Client;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Menu extends AppCompatActivity {
    int idJugador;
    String nombreMazo;
    RecyclerView recyclerView;
    AdaptadorMazos adaptadorMazos;
    List<Mazo> mazos;


    //iconos flotantes
    boolean botonesAbiertos = false;
    FloatingActionButton fab, fab1, fab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setTitle("Menú Principal");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.add_mazo);
        fab2 = (FloatingActionButton) findViewById(R.id.add_tarjeta);


        //recibiendo valores del login idJugador
        idJugador = getIntent().getIntExtra("idJugador", -1);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
        adaptadorMazos = new AdaptadorMazos(getApplicationContext(), mazos);
        recyclerView.setAdapter(adaptadorMazos);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String mazoNombre = mazos.get(position).getNombre();
                        int mazoContador = mazos.get(position).getContador();
                        Dialog d = new AlertDialog.Builder(recyclerView.getContext(), AlertDialog.BUTTON_POSITIVE)
                                .setTitle(mazoNombre)
                                .setNegativeButton("Cancelar", null)
                                .setItems(new String[]{"Repasar", "Ver tarjetas", "Ver repasos"}, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlg, int position) {
                                        if (position == 0) {
                                            API api = Client.getClient().create(API.class);
                                            Call<List<TarjetasConRespuestas>> call = api.getTarjetasConRespuestas(idJugador, mazoNombre);
                                            call.enqueue(new Callback<List<TarjetasConRespuestas>>() {
                                                @Override
                                                public void onResponse(Call<List<TarjetasConRespuestas>> call, Response<List<TarjetasConRespuestas>> response) {
                                                    if (response.isSuccessful()) {
                                                        List<TarjetasConRespuestas> tarjetasConRespuestas = response.body();
                                                        iniciarActividad(com.quiz.quizclient.Repaso.class, mazoNombre, mazoContador, tarjetasConRespuestas);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<List<TarjetasConRespuestas>> call, Throwable t) {

                                                }
                                            });

                                        }
                                        if (position == 1) {

                                            API api = Client.getClient().create(API.class);
                                            Call<List<Tarjeta>> call = api.getFromMazo(idJugador, mazoNombre);
                                            call.enqueue(new Callback<List<Tarjeta>>() {
                                                @Override
                                                public void onResponse(Call<List<Tarjeta>> call, Response<List<Tarjeta>> response) {
                                                    if (response.isSuccessful()) {
                                                        List<Tarjeta> tarjetas = response.body();
                                                        iniciarActividad(VerTarjetas.class, mazoNombre, mazoContador, tarjetas);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<List<Tarjeta>> call, Throwable t) {
                                                    Toast.makeText(getBaseContext(), "Error al recuperar tarjetas", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                        if (position == 2) {
                                            API api = Client.getClient().create(API.class);
                                            Call<List<Repaso>> call = api.getRepasosFrom(idJugador, mazoNombre);
                                            call.enqueue(new Callback<List<Repaso>>() {
                                                @Override
                                                public void onResponse(Call<List<Repaso>> call, Response<List<Repaso>> response) {
                                                    if (response.isSuccessful()) {
                                                        List<Repaso> repasos = response.body();
                                                        iniciarActividad(VerRepasos.class, mazoNombre, mazoContador, repasos);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<List<Repaso>> call, Throwable t) {
                                                    Toast.makeText(getBaseContext(), "Error al recuperar repasos" + t.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    }
                                }).create();
                        d.show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {


                        String mazoNombre = mazos.get(position).getNombre();
                        //guardamos la position porque cambiará tras el onResponse
                        int posicionActual = position;

                        Dialog borrar = new AlertDialog.Builder(recyclerView.getContext(), AlertDialog.BUTTON_POSITIVE)
                                .setTitle(mazoNombre)
                                .setNegativeButton("Cancelar", null)
                                .setItems(new String[]{"Borrar"}, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlg, int position) {

                                        API api = Client.getClient().create(API.class);
                                        Call<Void> call = api.deleteMazo(idJugador, mazoNombre);
                                        call.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if (response.isSuccessful()) {
                                                    Snackbar.make(view, "¡Mazo borrado!", Snackbar.LENGTH_LONG).show();
                                                    mazos.remove(mazos.get(posicionActual));
                                                    adaptadorMazos.setMazoList(mazos);
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Snackbar.make(view, "Error al borrar el mazo", Snackbar.LENGTH_LONG).show();

                                            }
                                        });

                                    }
                                }).create();
                        borrar.show();
                    }
                })
        );
        cargarMazos();

    }

    private void cargarMazos() {
        API api = Client.getClient().create(API.class);
        api.getMazosFrom(idJugador).enqueue(new Callback<List<Mazo>>() {
            @Override
            public void onResponse(Call<List<Mazo>> call, Response<List<Mazo>> response) {
                if (response.isSuccessful()) {
                    mazos = response.body();
                    //traza para el log, recorrer mazos
                    mazos.forEach(mazo -> Log.println(Log.DEBUG, "LOG", mazo.getNombre()));
                    adaptadorMazos.setMazoList(mazos);
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

    private void iniciarActividad(Class<?> actividad, String nombreMazo, int contador, List<?> lista) {
        if (contador != 0) {
            Intent intent = new Intent(this, actividad);
            //iniciando actividad
            intent.putExtra("idJugador", idJugador);
            intent.putExtra("nombreMazo", nombreMazo);
            //dependiendo si son Tarjetas o TarjetasConRespuestas se guarda para la siguiente actividad
            if (lista.get(0) instanceof Tarjeta) {
                intent.putExtra("tarjetas", (Serializable) lista);
            } else if (lista.get(0) instanceof TarjetasConRespuestas) {
                intent.putExtra("tarjetasConRespuestas", (Serializable) lista);
            } else {
                intent.putExtra("repasos", (Serializable) lista);
            }
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Primero asigne tarjetas al mazo ", Toast.LENGTH_LONG).show();
        }

    }


    public void add(View v) { //metodo encargado de agregar nuevo mazo

        View view = Menu.this.getLayoutInflater().inflate(R.layout.layout_crea_mazo, null);
        TextInputEditText txtNuevoMazo = view.findViewById(R.id.txtNuevoMazo);
        nombreMazo = txtNuevoMazo.getText().toString();
        AlertDialog dialog = new AlertDialog.Builder(Menu.this)
                .setTitle("Nuevo Mazo")
                .setView(view)
                .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        API api = Client.getClient().create(API.class);

                        Mazo mazo = new Mazo();
                        mazo.setNombre(txtNuevoMazo.getText().toString());
                        mazo.setIdJugador(idJugador);
                        Call<Void> call = api.createMazo(mazo);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "¡Mazo Creado!", Toast.LENGTH_LONG).show();
                                    //actualiza el recyclerView para mostrar el nuevo mazo
                                    mazos.add(mazo);
                                    adaptadorMazos.setMazoList(mazos);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Error creando el mazo " + response.code(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.println(Log.DEBUG, "LOG", t.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00B300"));
            }
        });
        dialog.show();

    }

    //abre actividad para crear tarjeta
    public void nuevaTarjeta(View v) {
        Intent intent = new Intent(this, CreaTarjeta.class);
        intent.putExtra("idJugador", idJugador);
        startActivity(intent);
    }

    //llamada menu flotante de botones
    public void menu_btns(View v) {
        if (!botonesAbiertos) {
            showFABMenu();
            fab.animate().rotationBy(225);
        } else {
            closeFABMenu();
            fab.animate().rotation(0);
        }
    }

    //animaciones menu flotante
    private void showFABMenu() {
        botonesAbiertos = true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));

    }

    //animaciones menu flotante
    private void closeFABMenu() {
        botonesAbiertos = false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);

    }


}