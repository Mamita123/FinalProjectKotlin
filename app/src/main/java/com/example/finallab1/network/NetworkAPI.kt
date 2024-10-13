package com.example.finallab1.network

import com.example.finallab1.db.ParliamentMember
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//6.10.2024 by Mamita Gurung 2115081
//This is a singleton class, responsible for sending and receiving HTTP requests
object NetworkAPI {
    private const val BASE_URL = "https://users.metropolia.fi/~mamitag/Kotlin/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)


    }
}
interface ApiService {
    @GET("data.json")
    fun loadMainData(): Call<List<ParliamentMember>>?

    @GET("extras.json")
    fun loadExtraData(): Call<List<ParliamentMember>>?


}



