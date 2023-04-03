package com.example.mvvmbsnews.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvmbsnews.model.Article

@Dao
interface ArticleDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article) :Long


    @Query("SELECT * from articles")
    fun getAllArticles():LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)


}