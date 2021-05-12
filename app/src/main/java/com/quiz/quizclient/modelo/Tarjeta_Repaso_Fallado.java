package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Tarjeta_Repaso_Fallado {

    @SerializedName("idTarjeta")
    private int idTarjeta;

    @SerializedName("idJugador")
    private int idJugador;

    @SerializedName("nombreMazo")
    private String nombreMazo;

    @SerializedName("fechaHoraInicio")
    private Date fechaHoraInicio;

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public String getNombreMazo() {
        return nombreMazo;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }
}
