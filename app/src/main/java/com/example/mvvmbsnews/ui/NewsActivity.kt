package com.example.mvvmbsnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.databinding.ActivityNewsBinding
import com.example.mvvmbsnews.db.ArticleDatabase
import com.example.mvvmbsnews.repository.NewsRepository
import com.example.mvvmbsnews.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    lateinit var activityNewsBinding: ActivityNewsBinding
    lateinit var navController: NavController

    lateinit var  viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNewsBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(activityNewsBinding.root)





        navController = findNavController(R.id.newsNavHostFragment)
        activityNewsBinding.bottomNavigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment -> {
                    activityNewsBinding.bottomNavigationView.visibility = View.GONE
                    supportActionBar?.hide()
                }

                R.id.onBoardFragment ->{
                    activityNewsBinding.bottomNavigationView.visibility = View.GONE
                    supportActionBar?.hide()
                }
                else -> {
                    activityNewsBinding.bottomNavigationView.visibility = View.VISIBLE
                    supportActionBar?.show()

                }
            }
        }






    }
}