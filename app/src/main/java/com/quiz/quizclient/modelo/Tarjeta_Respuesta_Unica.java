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

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Tarjeta_Respuesta_Unica() {
    }

    public Tarjeta_Respuesta_Unica(int idTarjeta, String valor) {
        this.idTarjeta = idTarjeta;
        this.valor = valor;
    }
}
