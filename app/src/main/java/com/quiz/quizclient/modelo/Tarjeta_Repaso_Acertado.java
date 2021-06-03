package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tarjeta_Repaso_Acertado implements Serializable {

    @SerializedName("Tarjeta_idTarjeta")
    private int Tarjeta_idTarjeta;

    @SerializedName("Repaso_idRepaso")
    private int Repaso_idRepaso;

    public traID getTraID() {
        return traID;
    }

    public void setTraID(traID traID) {
        this.traID = traID;
    }

    @SerializedName("traID")
    private traID traID;

    public Tarjeta_Repaso_Acertado(int idTarjeta) {
        this.Tarjeta_idTarjeta = idTarjeta;
    }

    public Tarjeta_Repaso_Acertado() {

    }

    public int getTarjeta_idTarjeta() {
        return Tarjeta_idTarjeta;
    }

    public void setTarjeta_idTarjeta(int tarjeta_idTarjeta) {
        this.Tarjeta_idTarjeta = tarjeta_idTarjeta;
    }

    public int getRepaso_idRepaso() {
        return Repaso_idRepaso;
    }

    public void setRepaso_idRepaso(int repaso_idRepaso) {
        this.Repaso_idRepaso = repaso_idRepaso;
    }

    @Override
    public String toString() {
        return "{" +
                "Tarjeta_idTarjeta=" + Tarjeta_idTarjeta +
                ", Repaso_idRepaso=" + Repaso_idRepaso +
                '}';
    }
}
