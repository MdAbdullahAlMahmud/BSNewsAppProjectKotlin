package com.example.mvvmbsnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.databinding.ActivityNewsBinding


class NewsActivity : AppCompatActivity() {

    lateinit var activityNewsBinding: ActivityNewsBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNewsBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(activityNewsBinding.root)

        navController = findNavController(R.id.newsNavHostFragment)
        activityNewsBinding.bottomNavigationView.setupWithNavController(navController)

    }
}