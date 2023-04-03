package com.example.mvvmbsnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.databinding.ActivityNewsBinding
import com.example.mvvmbsnews.db.ArticleDatabase
import com.example.mvvmbsnews.repository.NewsRepository
import com.example.mvvmbsnews.viewmodel.NewsViewModel


class NewsActivity : AppCompatActivity() {

    lateinit var activityNewsBinding: ActivityNewsBinding
    lateinit var navController: NavController

    lateinit var  viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNewsBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(activityNewsBinding.root)

        val newsRepository =  NewsRepository(ArticleDatabase(this))




        navController = findNavController(R.id.newsNavHostFragment)
        activityNewsBinding.bottomNavigationView.setupWithNavController(navController)








    }
}