package com.quiz.quizclient.modelo;

public class tarjeta {

    private int idTarjeta;
    private String tipoRespuesta;
    private int idJugador;
    private String nombreMazo;
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
