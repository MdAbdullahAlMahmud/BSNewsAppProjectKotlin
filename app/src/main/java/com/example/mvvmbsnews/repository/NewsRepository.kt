package com.example.mvvmbsnews.repository

import com.example.mvvmbsnews.api.NewsAPI
import com.example.mvvmbsnews.api.RetrofitInstance
import com.example.mvvmbsnews.db.ArticleDao
import com.example.mvvmbsnews.db.ArticleDatabase
import com.example.mvvmbsnews.model.Article
import retrofit2.http.Query
import javax.inject.Inject

class NewsRepository @Inject constructor (val articleDao: ArticleDao, val newsAPI: NewsAPI)  {


    suspend fun getBreakingNews(countryCode :String, pageNumber:Int) = newsAPI.getBreakingNews(countryCode,pageNumber)


    suspend fun searchNews(query: String, pageNumber: Int) = newsAPI.searchNew(query,pageNumber)

    suspend fun upsertArticle(article: Article) = articleDao.upsert(article)

    fun  getAllArticleFromDB() =articleDao.getAllArticles()

    suspend fun deleteArticleItem(article: Article) = articleDao.deleteArticle(article)
}