package com.example.mvvmbsnews.di

import android.content.Context
import com.example.mvvmbsnews.db.ArticleDao
import com.example.mvvmbsnews.db.ArticleDatabase
import com.example.mvvmbsnews.model.Article
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context) : ArticleDatabase{
        return  ArticleDatabase.invoke(context)
    }


    @Singleton
    @Provides
    fun  provideAppDAO(articleDatabase: ArticleDatabase):ArticleDao{
        return  articleDatabase.getArticleDao()
    }


    @Singleton
    @Provides
    fun providesFirebaseAuth ():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

}