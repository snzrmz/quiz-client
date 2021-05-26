package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

public class Tarjeta_Respuesta_Multiple {

    @SerializedName("idTarjeta")
    private int idTarjeta;

    public int getIdTarjeta() {

        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public Tarjeta_Respuesta_Multiple() {
    }

    public Tarjeta_Respuesta_Multiple(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }
}
