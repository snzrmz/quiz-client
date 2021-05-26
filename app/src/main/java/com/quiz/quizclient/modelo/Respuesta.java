package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

public class Respuesta {

    @SerializedName("idRespuesta")
    private int idRespuesta;
    @SerializedName("valor")
    private String valor;
    @SerializedName("correcta")
    private int correcta;
    @SerializedName("idTarjeta")
    private int idTarjeta;

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }



    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public String getValor() {
        return valor;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public Respuesta() {
    }

    public Respuesta(int idRespuesta, String valor, int correcta, int idTarjeta) {
        this.idRespuesta = idRespuesta;
        this.valor = valor;
        this.correcta = correcta;
        this.idTarjeta = idTarjeta;
    }
}

