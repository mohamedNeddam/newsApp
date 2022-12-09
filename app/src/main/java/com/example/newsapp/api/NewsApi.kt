package com.example.newsapp.ui.api

import com.example.newsapp.ui.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        page: Int = 1,
        @Query("apikey")
        apiKey: String = API_KEY
    )


    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        page: Int = 1,
        @Query("apikey")
        apiKey: String = API_KEY
    )
}
