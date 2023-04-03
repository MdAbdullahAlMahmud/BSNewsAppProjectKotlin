package com.example.mvvmbsnews.repository

import com.example.mvvmbsnews.api.RetrofitInstance
import com.example.mvvmbsnews.db.ArticleDatabase

class NewsRepository(articleDatabase: ArticleDatabase)  {


    suspend fun getBreakingNews(countryCode :String, pageNumber:Int) = RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)


}