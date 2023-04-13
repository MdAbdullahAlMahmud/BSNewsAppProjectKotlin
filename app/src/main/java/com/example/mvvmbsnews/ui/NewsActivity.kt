package com.example.mvvmbsnews.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    lateinit var activityNewsBinding: ActivityNewsBinding
    lateinit var navController: NavController

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
                R.id.authenticationFragment ->{
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