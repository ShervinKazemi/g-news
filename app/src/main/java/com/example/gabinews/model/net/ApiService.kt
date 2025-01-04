package com.example.gabinews.model.net

import com.example.gabinews.model.data.NewsResponse
import com.example.gabinews.util.Constants.API_KEY
import com.example.gabinews.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers(API_KEY)
    @GET("top-headlines")
    suspend fun getTopNews(
        @Query("category") category:String = "technology"
    ) :NewsResponse

}

fun createApiService() :ApiService {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}