package com.example.app_systempar
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Metodos {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.147:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        /*return Retrofit.Builder()
            .baseUrl("http://172.16.159.175:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()*/
    }
}