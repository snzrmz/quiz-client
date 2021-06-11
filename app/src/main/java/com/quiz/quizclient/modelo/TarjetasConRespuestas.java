package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TarjetasConRespuestas implements Serializable {

    @SerializedName("idTarjeta")
    private int idTarjeta;
    @SerializedName("tipoRespuesta")
    private String tipoRespuesta;
    @SerializedName("idJugador")
    private int idJugador;
    @SerializedName("pregunta")
    private String pregunta;
    @SerializedName("valor")
    private String valor;
    @SerializedName("correcta")
    private int correcta;
    @SerializedName("recursoRuta")
    private String recursoRuta;

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public String getTipoRespuesta() {
        return tipoRespuesta;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String getValor() {
        return valor;
    }

    public int getCorrecta() {
        return correcta;
    }

    public String getRecursoRuta() {
        return recursoRuta;
    }

    @Override
    public String toString() {
        return "TarjetasConRespuestas{" +
                "idTarjeta=" + idTarjeta +
                ", tipoRespuesta='" + tipoRespuesta + '\'' +
                ", idJugador=" + idJugador +
                ", pregunta='" + pregunta + '\'' +
                ", valor='" + valor + '\'' +
                ", correcta=" + correcta +
                ", recursoRuta='" + recursoRuta + '\'' +
                '}';
    }
}
