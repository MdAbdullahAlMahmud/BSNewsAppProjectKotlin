package com.example.mvvmbsnews.repository

import com.example.mvvmbsnews.api.RetrofitInstance
import com.example.mvvmbsnews.db.ArticleDatabase
import retrofit2.http.Query

class NewsRepository(articleDatabase: ArticleDatabase)  {


    suspend fun getBreakingNews(countryCode :String, pageNumber:Int) = RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)


    suspend fun searchNews(query: String, pageNumber: Int) = RetrofitInstance.api.searchNew(query,pageNumber)

}