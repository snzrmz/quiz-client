package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

public class Tarjeta_Respuesta_Unica {

    @SerializedName("idTarjeta")
    private int idTarjeta;

    @SerializedName("valor")
    private String valor;

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public String getValor() {
        return valor;
    }
}
