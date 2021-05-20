package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

public class Respuesta {
    @SerializedName("idRespuesta")
    private int idRespuesta;
    @SerializedName("valor")
    private String valor;
    @SerializedName("correcta")
    private boolean correcta;
    @SerializedName("idTarjeta")
    private int idTarjeta;

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setCorrecta(boolean correcta) {
        this.correcta = correcta;
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

    public boolean isCorrecta() {
        return correcta;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }
}

