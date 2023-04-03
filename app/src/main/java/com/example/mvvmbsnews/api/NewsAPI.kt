package com.example.mvvmbsnews.api

import com.example.mvvmbsnews.model.NewsResponse
import com.example.mvvmbsnews.util.Constant.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode : String = "us",
        @Query("page")
        pageNumber:Int = 1,
        @Query("apiKey")
        apikey : String =API_KEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNew(
        @Query("q")
        searchQuery : String ,
        @Query("page")
        pageNumber:Int = 1,
        @Query("apiKey")
        apikey : String =API_KEY
    ):Response<NewsResponse>




}