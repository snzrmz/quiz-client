package com.quiz.quizclient.restclient;

import com.quiz.quizclient.modelo.Jugador;
import com.quiz.quizclient.modelo.Mazo;
import com.quiz.quizclient.modelo.Repaso;
import com.quiz.quizclient.modelo.Respuesta;
import com.quiz.quizclient.modelo.Tarjeta;
import com.quiz.quizclient.modelo.Tarjeta_Respuesta_Multiple;
import com.quiz.quizclient.modelo.Tarjeta_Respuesta_Unica;
import com.quiz.quizclient.modelo.TarjetasConRespuestas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {


    //Jugadores----------------------------------
    @GET("jugadores/{id}")
    Call<Jugador> getJugadorById(@Path("id") int id);

    @POST("jugadores/")
    Call<Void> createJugador(@Body Jugador jugador);

    @GET("login/{email}")
    Call<Jugador> getJugadorByEmail(@Path("email") String email);

    @PUT("jugadores/")
    Call<Void> updateJugador(@Body Jugador jugador);

    //Mazos--------------------------------------
    @GET("jugadores/{id}/mazos")
    Call<List<Mazo>> getMazosFrom(@Path("id") int id);

    @POST("jugadores/{id}/mazos/")
    Call<Void> createMazo(@Body Mazo mazo);

    @DELETE("jugadores/{id}/mazos/{nombreMazo}")
    Call<Void> deleteMazo(@Path("id") int id, @Path("nombreMazo") String nombreMazo);

    @PUT("jugadores/{id}/mazos/{nombreMazo}")
    Call<Void> updateMazo(@Path("id") int id, @Path("nombreMazo") String nombreMazo, @Body Mazo mazo);

    //Tarjetas-------------------------------------
    @POST("jugadores/{id}/mazos/{nombreMazo}/tarjetas")
    Call<Void> createTarjeta(@Body Tarjeta tarjeta);

    @GET("jugadores/{id}/mazos/{nombreMazo}/tarjetas")
    Call<List<Tarjeta>> getFromMazo(@Path("id") int id, @Path("nombreMazo") String nombreMazo);

    //Respuestas--------------------------------------------
    @GET("jugadores/{id}/mazos/{nombreMazo}/tarjetas/respuestas")
    Call<List<TarjetasConRespuestas>> getTarjetasConRespuestas(@Path("id") int idJugador, @Path("nombreMazo") String mazoNombre);

    //Respasos----------------------------------------------
    @GET("jugadores/{id}/mazos/{nombreMazo}/repasos")
    Call<List<Repaso>> getRepasosFrom(@Path("id") int id, @Path("nombreMazo") String nombreMazo);

    @POST("jugadores/{id}/mazos/{nombreMazo}/repasos")
    Call<Void> createRepaso(@Body Repaso repaso);


    //TarjetaRespuestaUnica
    @POST("jugadores/{id}/mazos/{nombreMazo}/tarjetas/unica")
    Call<Void> createRespuestaOfTarjetaUnica(@Body Tarjeta_Respuesta_Unica tru);

    @POST("jugadores/{id}/mazos/{nombreMazo}/tarjetas/multiple")
    Call<Void> createRespuestaTarjetaMultiple(@Body Tarjeta_Respuesta_Multiple trm);

    @POST("jugadores/{id}/mazos/{nombreMazo}/tarjetas/multiple/respuestas")
    Call<Void> createRespuesta(@Body List<Respuesta> respuestas);
}



