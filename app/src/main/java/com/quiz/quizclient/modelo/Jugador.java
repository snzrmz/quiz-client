package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

public class Jugador {

    @SerializedName("email")
    private String email;

    @SerializedName("contrasena")
    private String password;

    @SerializedName("fechaCreacion")
    private String fechaCreacion;

    @SerializedName("idJugador")
    private int idJugador;

    @SerializedName("usuario")
    private String usuario;

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Jugador() {
    }

    public Jugador(String email, String password, String fechaCreacion, int idJugador, String usuario) {
        this.email = email;
        this.password = password;
        this.fechaCreacion = fechaCreacion;
        this.idJugador = idJugador;
        this.usuario = usuario;
    }
}
