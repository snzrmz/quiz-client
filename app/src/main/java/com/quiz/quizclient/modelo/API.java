package com.quiz.quizclient.modelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @GET("1")
    Call<Jugador> getJugador();



}
