package com.quiz.quizclient.modelo;

import java.util.Date;

public class jugador {
    private String email;
    private String password;
    private Date fechaCreacion;
    private int idJugador;
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
