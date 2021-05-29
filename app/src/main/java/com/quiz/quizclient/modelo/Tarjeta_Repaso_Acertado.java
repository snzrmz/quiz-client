package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tarjeta_Repaso_Acertado implements Serializable {

    @SerializedName("tarjeta_idTarjeta")
    private int idTarjeta;

    @SerializedName("repaso_idRepaso")
    private int idRepaso;

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
}
