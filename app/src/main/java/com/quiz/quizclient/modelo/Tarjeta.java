package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tarjeta {

    @SerializedName("idTarjeta")
    private int idTarjeta;

    @SerializedName("tipoRespuesta")
    private String tipoRespuesta;

    @SerializedName("idJugador")
    private int idJugador;

    @SerializedName("nombreMazo")
    private String nombreMazo;

    @SerializedName("recurso")
    private String recurso;

    @SerializedName("pregunta")
    private String pregunta;

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getTipoRespuesta() {
        return tipoRespuesta;
    }

    public void setTipoRespuesta(String tipoRespuesta) {
        this.tipoRespuesta = tipoRespuesta;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreMazo() {
        return nombreMazo;
    }

    public void setNombreMazo(String nombreMazo) {
        this.nombreMazo = nombreMazo;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    public Tarjeta(){}

    public Tarjeta(int idTarjeta, String tipoRespuesta, int idJugador, String nombreMazo, String recurso, String pregunta) {
        this.idTarjeta = idTarjeta;
        this.tipoRespuesta = tipoRespuesta;
        this.idJugador = idJugador;
        this.nombreMazo = nombreMazo;
        this.recurso = recurso;
        this.pregunta = pregunta;
    }
}