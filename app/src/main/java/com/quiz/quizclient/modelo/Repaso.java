package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Repaso {

    @SerializedName("idJugador")
    private int idJugador;

    @SerializedName("nombreMazo")
    private String nombreMazo;

    @SerializedName("fechaHoraInicio")
    private Date fechaHoraInicio;

    @SerializedName("fin")
    private Date fin;

    public int getIdJugador() {
        return idJugador;
    }

    public String getNombreMazo() {
        return nombreMazo;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public Date getFin() {
        return fin;
    }
}
