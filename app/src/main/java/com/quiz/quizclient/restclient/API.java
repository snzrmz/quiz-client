package com.quiz.quizclient.restclient;

import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.modelo.Mazo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {

    @GET("jugadores/{id}")
    Call<Jugador> getJugadorById(@Path("id") int id);

    @GET("login/{email}")
    Call<Jugador> getJugadorByEmail(@Path("email") String email);

    @GET("jugadores/{id}/mazos")
    Call<List<Mazo>> getMazosFrom(@Path("id") int id);


}
