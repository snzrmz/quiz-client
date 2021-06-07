package com.quiz.quizclient.restclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static final String ip = "192.168.1.42";
    private static final String port = "8080";
    public static final String BASEURL = "http://" + ip + ":" + port + "/quiz-server/api/";

    public static Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
