package com.example.mvvmbsnews.repository

import com.example.mvvmbsnews.api.RetrofitInstance
import com.example.mvvmbsnews.db.ArticleDatabase
import com.example.mvvmbsnews.model.Article
import retrofit2.http.Query

class NewsRepository(val articleDatabase: ArticleDatabase)  {


    suspend fun getBreakingNews(countryCode :String, pageNumber:Int) = RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)


    suspend fun searchNews(query: String, pageNumber: Int) = RetrofitInstance.api.searchNew(query,pageNumber)

    suspend fun upsertArticle(article: Article) = articleDatabase.getArticleDao().upsert(article)

    fun  getAllArticleFromDB() = articleDatabase.getArticleDao().getAllArticles()

    suspend fun deleteArticleItem(article: Article) = articleDatabase.getArticleDao().deleteArticle(article)
}