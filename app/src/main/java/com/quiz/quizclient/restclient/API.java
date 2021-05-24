package com.quiz.quizclient.restclient;

import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.modelo.Mazo;
import com.quiz.quizclient.modelo.Tarjeta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

    @GET("jugadores/{id}")
    Call<Jugador> getJugadorById(@Path("id") int id);

    @GET("login/{email}")
    Call<Jugador> getJugadorByEmail(@Path("email") String email);

    @GET("jugadores/{id}/mazos")
    Call<List<Mazo>> getMazosFrom(@Path("id") int id);

    @GET("jugadores/{id}/mazos/{nombreMazo}/tarjetas")
    Call<List<Tarjeta>> getFromMazo(@Path("id") int id, @Path("nombreMazo") String nombreMazo);

    @POST("jugadores/{id}/mazos/")
    Call<Void> newMazo(@Body Mazo mazo);

    @POST("jugadores/{id}/mazos/{nombreMazo}/tarjetas")
    Call<Void> newTarjeta(@Body Tarjeta tarjeta);

}



