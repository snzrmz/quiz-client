package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

public class Tarjeta {

    @SerializedName("idTarjeta")
    private int idTarjeta;

    @SerializedName("tipoRespuesta")
    private String tipoRespuesta;

    @SerializedName("idJugador")
    private int idJugador;

    @SerializedName("nombreMazo")
    private String nombreMazo;

    @SerializedName("pregunta")
    private String pregunta;

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public String getTipoRespuesta() {
        return tipoRespuesta;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public String getNombreMazo() {
        return nombreMazo;
    }

    public String getPregunta() {
        return pregunta;
    }
}
