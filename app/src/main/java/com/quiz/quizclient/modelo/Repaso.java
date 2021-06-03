package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Repaso implements Serializable {

    @SerializedName("idJugador")
    private int idJugador;
    @SerializedName("idRepaso")
    private int idRepaso;
    @SerializedName("nombreMazo")
    private String nombreMazo;

    @SerializedName("fechaHoraInicio")
    private String fechaHoraInicio;

    @SerializedName("fin")
    private boolean fin;

    @SerializedName("tarjetaRepasoAcertado")
    private List<Tarjeta_Repaso_Acertado> tarjetaRepasoAcertado;

    @SerializedName("tarjetaRepasoFallado")
    private List<Tarjeta_Repaso_Fallado> tarjetaRepasoFallado;

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public void setNombreMazo(String nombreMazo) {
        this.nombreMazo = nombreMazo;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public String getFechaDiferencia() {
        String resultado = null;
        String formato = "yyyy-MM-dd'T'HH:mm:ss";

        LocalDateTime hoy = LocalDateTime.now();
        LocalDateTime repasado =
                LocalDateTime.parse(this.fechaHoraInicio, DateTimeFormatter.ofPattern(formato));

        int numDias = (int) ChronoUnit.DAYS.between(repasado, hoy);

        if (numDias == 0) {
            resultado = "menos de 1 día";
        } else if (numDias == 1) {
            resultado = "hace 1 día";
        } else if (numDias >= 2 && numDias < 365) {
            resultado = "hace " + numDias + " días";
        } else if (numDias == 365) {
            resultado = "hace 1 año";
        } else if (numDias > 365) {
            resultado = "hace más de 1 año";
        }
        return resultado;

    }

    public Repaso(int idJugador, String nombreMazo, String fechaHoraInicio, boolean fin, List tarjetaRepasoAcertado, List tarjetaRepasoFallado) {
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

    public int getIdRepaso() {
        return idRepaso;
    }

    public void setIdRepaso(int idRepaso) {
        this.idRepaso = idRepaso;
    }

    @Override
    public String toString() {
        return "Repaso{" +
                "idJugador=" + idJugador +
                ", idRepaso=" + idRepaso +
                ", nombreMazo='" + nombreMazo + '\'' +
                ", fechaHoraInicio='" + fechaHoraInicio + '\'' +
                ", fin=" + fin +
                ", tarjetaRepasoAcertado=" + tarjetaRepasoAcertado +
                ", tarjetaRepasoFallado=" + tarjetaRepasoFallado +
                '}';
    }
}
