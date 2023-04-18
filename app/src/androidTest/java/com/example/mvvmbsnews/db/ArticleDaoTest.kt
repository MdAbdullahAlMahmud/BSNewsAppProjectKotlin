package com.example.mvvmbsnews.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.mvvmbsnews.getOrAwaitValue
import com.example.mvvmbsnews.model.Article
import com.example.mvvmbsnews.model.Source
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ArticleDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var articleDatabase: ArticleDatabase
    private lateinit var articleDao: ArticleDao


    @Before
    fun setup(){

        articleDatabase = Room.
        inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),ArticleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        articleDao = articleDatabase.getArticleDao()
    }

    @After
    fun teardown(){
        articleDatabase.close()

    }


    /**
     * Unit test for insert
     * Add favourite article item to room database
     */


    @Test
    fun insertArticleItem() = runBlockingTest{

        val articleItem = Article(1,"Abdullah","test content","Test desc","18 April 2023",Source(),"Title","www.google.com","test")

        articleDao.upsert(articleItem)

        val allArticleItems = articleDao.getAllArticles().getOrAwaitValue()
        assertThat(allArticleItems).contains(articleItem)

    }

}