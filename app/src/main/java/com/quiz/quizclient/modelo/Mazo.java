package com.quiz.quizclient.modelo;

import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

public class Mazo {

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("idJugador")
    private int idJugador;

    private int contador;

    public int getContador() {
        return contador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public Mazo(String nombre, int idJugador) {
        this.nombre = nombre;
        this.idJugador = idJugador;
    }

    public Mazo() {
    }

    @Override
    public String toString() {
        return "Mazo{" +
                "nombre:'" + nombre +
                "idJugador:" + idJugador +
                '}';
    }
}
