package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Repaso implements Serializable {

    @SerializedName("idJugador")
    private int idJugador;

    @SerializedName("nombreMazo")
    private String nombreMazo;

    @SerializedName("fechaHoraInicio")
    private Date fechaHoraInicio;

    @SerializedName("fin")
    private boolean fin;

    @SerializedName("tarjetaRepasoAcertado")
    private List<Tarjeta_Repaso_Acertado> tarjetaRepasoAcertado;

    @SerializedName("tarjetaRepasoFallado")
    private List<Tarjeta_Repaso_Fallado> tarjetaRepasoFallado;

    public Repaso(int idJugador, String nombreMazo, Date fechaHoraInicio, boolean fin, List tarjetaRepasoAcertado, List tarjetaRepasoFallado) {
        this.idJugador = idJugador;
        this.nombreMazo = nombreMazo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fin = fin;
        this.tarjetaRepasoAcertado = new ArrayList<>();
        this.tarjetaRepasoFallado = new ArrayList<>();
    }

    public Repaso() {
        super();
        this.tarjetaRepasoAcertado = new ArrayList<>();
        this.tarjetaRepasoFallado = new ArrayList<>();
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

    public boolean getFin() {
        return fin;
    }

    public List<Tarjeta_Repaso_Acertado> getTarjetaRepasoAcertado() {
        return tarjetaRepasoAcertado;
    }

    public void setTarjetaRepasoAcertado(List<Tarjeta_Repaso_Acertado> tarjetaRepasoAcertado) {
        this.tarjetaRepasoAcertado = tarjetaRepasoAcertado;
    }

    public List<Tarjeta_Repaso_Fallado> getTarjetaRepasoFallado() {
        return tarjetaRepasoFallado;
    }

    public void setTarjetaRepasoFallado(List<Tarjeta_Repaso_Fallado> tarjetaRepasoFallado) {
        this.tarjetaRepasoFallado = tarjetaRepasoFallado;
    }
}
