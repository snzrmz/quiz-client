package com.quiz.quizclient.restclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {


    public static Retrofit getClient(String ip, String puerto) {

        return new Retrofit.Builder()
                .baseUrl("http://" + ip + ":" + puerto + "/quiz-server/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
