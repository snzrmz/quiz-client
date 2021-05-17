package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

public class Mazo {

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("idJugador")
    private int idJugador;

    private int contador;

    public int getContador() {
        return contador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdJugador() {
        return idJugador;
    }
}
