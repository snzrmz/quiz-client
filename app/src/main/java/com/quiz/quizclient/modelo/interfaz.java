package com.quiz.quizclient.modelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface interfaz {

    @GET("jugadores")
    Call<List<jugador>> getPosts();

}
