package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tarjeta implements Serializable {

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

    @SerializedName("recurso")
    private String recurso;

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public void setTipoRespuesta(String tipoRespuesta) {
        this.tipoRespuesta = tipoRespuesta;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public void setNombreMazo(String nombreMazo) {
        this.nombreMazo = nombreMazo;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

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

    public Tarjeta() {
    }

    public Tarjeta(int idTarjeta, String tipoRespuesta, int idJugador, String nombreMazo, String pregunta, String recurso) {
        this.idTarjeta = idTarjeta;
        this.tipoRespuesta = tipoRespuesta;
        this.idJugador = idJugador;
        this.nombreMazo = nombreMazo;
        this.pregunta = pregunta;
        this.recurso = recurso;
    }
}