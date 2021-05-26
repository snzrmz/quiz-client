package com.quiz.quizclient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizclient.modelo.Tarjeta;
import com.quiz.quizclient.modelo.Tarjeta_Repaso_Acertado;
import com.quiz.quizclient.modelo.Tarjeta_Repaso_Fallado;
import com.quiz.quizclient.modelo.TarjetasConRespuestas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repaso extends AppCompatActivity {

    String nombreMazo;
    TextView txtPregunta, txtRespuesta, txtNumTarjeta;
    LinearLayout lLRespuestas;
    ImageView iVRecurso;
    ConstraintLayout cL;

    int idJugador, indiceTarjetaActual, numTarjetas, contadorCorrectas;
    EditText etRespuesta;
    Map<Integer, List<TarjetasConRespuestas>> mapTarjetasRespuestas;
    boolean esMultiple;
    List<String> respuestasMarcadas;
    List<Integer> claves;
    List<Tarjeta> tarjetas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repaso);
        idJugador = getIntent().getIntExtra("idJugador", -1);
        nombreMazo = getIntent().getStringExtra("nombreMazo");
        getSupportActionBar().setTitle("Repasando " + nombreMazo);
        txtPregunta = findViewById(R.id.txtRespuesta);
        lLRespuestas = findViewById(R.id.lLRespuestas);
        txtNumTarjeta = findViewById(R.id.txtNumTarjeta);
        etRespuesta = findViewById(R.id.etRespuesta);
        iVRecurso = findViewById(R.id.iVRecurso);
        cL = findViewById(R.id.cLayout);
        Button btnAceptar = findViewById(R.id.btnAceptar);
        List<String> respuestasUsuario = new ArrayList<>();
        //recibiendo valores de mazos
        idJugador = getIntent().getIntExtra("idJugador", -1);
        List<TarjetasConRespuestas> tarjetasConRespuestas = (List<TarjetasConRespuestas>) getIntent().getSerializableExtra("tarjetasConRespuestas");
        tarjetas = new ArrayList<>();
        //HashMap para deshacernos de repetidos
        mapTarjetasRespuestas = new HashMap<>();
        /*Shortcut for adding to List in a HashMap: https://stackoverflow.com/a/3019388*/
        //recorremos las tarjetas con respuestas, si aún no ha sido añadido se ejecutará computeIfAbsent
        //que creará por cada clave del hashmap un arraylist y si no lo añdirá a este.
        //además si aún no ha sido añadido también lo agregamos a un objeto de tipo tarjeta que se utilizará
        //para visualizar como ha sido el repaso.
        for (TarjetasConRespuestas tcr : tarjetasConRespuestas) {
            mapTarjetasRespuestas.computeIfAbsent(tcr.getIdTarjeta(), k -> {
                tarjetas.add(new Tarjeta(tcr.getIdTarjeta(), "", -1, "", tcr.getPregunta(), tcr.getRecursoRuta()));
                return new ArrayList<>();
            }).add(tcr);
        }

        //Guardamos las claves
        claves = new ArrayList<>(mapTarjetasRespuestas.keySet());
        //Establecemos valores
        numTarjetas = mapTarjetasRespuestas.size();
        indiceTarjetaActual = 0;
        contadorCorrectas = 0;
        txtNumTarjeta.setText(indiceTarjetaActual + 1 + "/" + numTarjetas);

        //segun sea unica o multiple se estableceran las vistas acordemente
        if (mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual)).get(0).getTipoRespuesta().equals("UNICA")) {
            establecerTarjetaSimple(mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual)).get(0));
        } else {
            establecerTarjetaMultiple(mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual)));
        }

    }

    void establecerTarjetaSimple(TarjetasConRespuestas tjr) {
        iVRecurso.setVisibility(tjr.getRecursoRuta() != null ? View.VISIBLE : View.GONE);
        esMultiple = false;
        txtPregunta.setText(tjr.getPregunta());
        lLRespuestas.setOrientation(LinearLayout.HORIZONTAL);
        //Si se han eliminado todas las vistas agregar la necesaria
        Log.println(Log.DEBUG, "LOG", String.valueOf(tjr.getRecursoRuta() != null));
        if (lLRespuestas.getChildCount() == 0) {
            lLRespuestas.addView(etRespuesta);
        }
    }

    void establecerTarjetaMultiple(List<TarjetasConRespuestas> ltjr) {
        iVRecurso.setVisibility(ltjr.get(0).getRecursoRuta() != null ? View.VISIBLE : View.GONE);
        esMultiple = true;
        txtPregunta.setText(ltjr.get(0).getPregunta());
        lLRespuestas.removeAllViews();
        lLRespuestas.setOrientation(LinearLayout.VERTICAL);
        respuestasMarcadas = new ArrayList<>();

        for (int i = 0; i < ltjr.size(); i++) {
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setText(ltjr.get(i).getValor());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                //Actualizamos el estado de los checkboxes
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        respuestasMarcadas.add(checkBox.getText().toString());
                    } else {
                        respuestasMarcadas.remove(checkBox.getText().toString());
                    }
                }
            });
            //agregamos la vista
            lLRespuestas.addView(checkBox);
        }
    }

    public void onAceptar(View v) {
        Tarjeta_Repaso_Acertado tra;
        Tarjeta_Repaso_Fallado trf;
        if (esMultiple) {

            //recorremos la tarjeta con respuesta multiple, si
            List<String> respuestas = new ArrayList<>();
            //recorremos las respuestas correctas de las tarjetas y las agregamos a un array temporal
            for (TarjetasConRespuestas tarjetasConRespuestas : mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual))) {
                if (tarjetasConRespuestas.getCorrecta() == 1) {
                    respuestas.add(tarjetasConRespuestas.getValor());
                }
            }
            //ordenamos el array y comprobamos sin son iguales.
            Collections.sort(respuestas);
            Collections.sort(respuestasMarcadas);
            if (respuestas.equals(respuestasMarcadas)) {
                Log.println(Log.DEBUG, "LOG", "Respuesta correctas " + respuestas);
                //establecemos la propiedad que mostrará los resultados al final y añadimos al contador
                tarjetas.get(indiceTarjetaActual).setCorrecta(true);
                contadorCorrectas++;

            } else {
                Log.println(Log.DEBUG, "LOG", "Respuesta incorrecta, correctas: " + respuestas + " marcadas: " + respuestasMarcadas);
            }
        } else {
            Log.println(Log.DEBUG, "LOG", mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual)).get(0).getValor());
            if (etRespuesta.getText().toString().equals(mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual)).get(0).getValor())) {
                Log.println(Log.DEBUG, "LOG", "Respueta correcta");
                //establecemos la propiedad que mostrará los resultados al final y añadimos al contador
                tarjetas.get(indiceTarjetaActual).setCorrecta(true);
                contadorCorrectas++;
            }
        }
        indiceTarjetaActual++;
        // si aún hay tarjetas que responder
        if (indiceTarjetaActual < numTarjetas) {
            txtNumTarjeta.setText(txtNumTarjeta.getText().toString().replaceFirst("\\d+", String.valueOf(indiceTarjetaActual + 1)));
            List<TarjetasConRespuestas> keySiguientePregunta = mapTarjetasRespuestas.get(claves.get(indiceTarjetaActual));
            etRespuesta.setText("");
            if (keySiguientePregunta.get(0).getTipoRespuesta().equals("UNICA")) {
                establecerTarjetaSimple(keySiguientePregunta.get(0));

            } else {
                establecerTarjetaMultiple(keySiguientePregunta);
            }
        } else { //persistir repaso y mostrar resultados

            cL.removeView(findViewById(R.id.cvTarjeta));


            RecyclerView rv = new RecyclerView(this);
            rv.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            for (Tarjeta tarjeta : tarjetas) {
                Log.println(Log.DEBUG, "LOG", tarjeta.toString());
            }

            cL.addView(rv);
            rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false));
            AdaptadorTarjetas adaptadorTarjetas = new AdaptadorTarjetas(getApplicationContext(), tarjetas, true);
            rv.setAdapter(adaptadorTarjetas);

        }


    }

}

