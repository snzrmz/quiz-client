package com.quiz.quizclient.restclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static Retrofit retrofit = null;
    private static final String ip = "192.168.1.42";
    private static final String port = "8080";
    private static String BASEURL = "http://" + ip + ":" + port + "/quiz-server/api/";

    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
