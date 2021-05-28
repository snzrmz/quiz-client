package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tarjeta_Repaso_Fallado implements Serializable {

    @SerializedName("tarjeta_idTarjeta")
    private int idTarjeta;

    @SerializedName("repaso_idRepaso")
    private int idRepaso;

}
