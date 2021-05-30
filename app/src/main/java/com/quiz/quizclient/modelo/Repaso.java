package com.quiz.quizclient.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Repaso implements Serializable {

    @SerializedName("idJugador")
    private int idJugador;

    @SerializedName("nombreMazo")
    private String nombreMazo;

    @SerializedName("fechaHoraInicio")
    private Date fechaHoraInicio;

    @SerializedName("fin")
    private boolean fin;

    @SerializedName("tarjetaRepasoAcertado")
    private List<Tarjeta_Repaso_Acertado> tarjetaRepasoAcertado;

    @SerializedName("tarjetaRepasoFallado")
    private List<Tarjeta_Repaso_Fallado> tarjetaRepasoFallado;

    public String getFechaDiferencia() {
        String resultado = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date hoy = new Date();
        sdf.format(hoy);
        Date repasado = this.fechaHoraInicio;
        long diffMilisegundos = (hoy.getTime() - repasado.getTime());
        long diffDias = TimeUnit.DAYS.convert(diffMilisegundos, TimeUnit.MILLISECONDS);
        int numDias = (int) diffDias;

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

    public Repaso(int idJugador, String nombreMazo, Date fechaHoraInicio, boolean fin, List tarjetaRepasoAcertado, List tarjetaRepasoFallado) {
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

    public Date getFechaHoraInicio() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(fechaHoraInicio.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null) {
            return new Date();
        }
        return date;
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
}
