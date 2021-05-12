package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

public class Mazo {

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("idJugador")
    private int idJugador;

    public String getNombre() {
        return nombre;
    }

    public int getIdJugador() {
        return idJugador;
    }
}
