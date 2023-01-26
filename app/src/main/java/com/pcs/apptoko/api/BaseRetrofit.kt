package com.pcs.apptoko.api

import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class BaseRetrofit {
    val endPoint : ApiEndPoint

    get(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            //.protocols(Collections.singletonList(Protocol.HTTP_1_1))
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apptokopcs.000webhostapp.com/index.php/Api_pcs/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiEndPoint::class.java)
    }
}