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

    @SerializedName("recursoRuta")
    private String recursoRuta;

    //atributo no serializado
    private boolean correcta;

    public String getRecursoRuta() {
        return recursoRuta;
    }

    public void setRecursoRuta(String recursoRuta) {
        this.recursoRuta = recursoRuta;
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

    public Tarjeta(int idTarjeta, String tipoRespuesta, int idJugador, String nombreMazo, String pregunta, String recursoRuta) {
        this.idTarjeta = idTarjeta;
        this.tipoRespuesta = tipoRespuesta;
        this.idJugador = idJugador;
        this.nombreMazo = nombreMazo;
        this.pregunta = pregunta;
        this.recursoRuta = recursoRuta;
    }


    @Override
    public String toString() {
        return "Tarjeta{" +
                "idTarjeta=" + idTarjeta +
                ", tipoRespuesta='" + tipoRespuesta + '\'' +
                ", idJugador=" + idJugador +
                ", nombreMazo='" + nombreMazo + '\'' +
                ", pregunta='" + pregunta + '\'' +
                ", recursoRuta='" + recursoRuta + '\'' +
                ", correcta=" + correcta +
                '}';
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public void setCorrecta(boolean correcta) {
        this.correcta = correcta;
    }
}