package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Jugador {

    @SerializedName("email")
    private String email;

    @SerializedName("contrasena")
    private String password;

    @SerializedName("fechaCreacion")
    private Date fechaCreacion;

    @SerializedName("idJugador")
    private int idJugador;

    @SerializedName("usuario")
    private String usuario;

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public String getUsuario() {
        return usuario;
    }
}
