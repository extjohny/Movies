package ru.abdulkhalikov.core_network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://api.poiskkino.dev"

    private val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val oldRequest = chain.request()
        val newRequest = oldRequest.newBuilder()
            .addHeader("X-API-KEY", "P3GC0XQ-CQ748JW-Q5QP698-Q2AX0PJ")
            .build()
        chain.proceed(newRequest)
    }.build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
