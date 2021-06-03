package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tarjeta_Repaso_Fallado implements Serializable {

    @SerializedName("Tarjeta_idTarjeta")
    private int idTarjeta;


    @SerializedName("Repaso_idRepaso")
    private int idRepaso;

    @SerializedName("traID")
    private traID traID;

    public traID getTraID() {
        return traID;
    }

    public void setTraID(traID traID) {
        this.traID = traID;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public int getIdRepaso() {
        return idRepaso;
    }

    public void setIdRepaso(int idRepaso) {
        this.idRepaso = idRepaso;
    }


    public Tarjeta_Repaso_Fallado() {
    }
}
