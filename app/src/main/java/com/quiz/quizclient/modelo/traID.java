package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class traID implements Serializable {
    @SerializedName("tarjeta_idTarjeta")
    private int getTarjeta_idTarjeta() {
        return tarjeta_idTarjeta;
    }

    public void setTarjeta_idTarjeta(int tarjeta_idTarjeta) {
        this.tarjeta_idTarjeta = tarjeta_idTarjeta;
    }

    @SerializedName("repaso_idRepaso")
    private int getRepaso_idRepaso() {
        return repaso_idRepaso;
    }

    public void setRepaso_idRepaso(int repaso_idRepaso) {
        this.repaso_idRepaso = repaso_idRepaso;
    }

    private int tarjeta_idTarjeta;
    private int repaso_idRepaso;
}
