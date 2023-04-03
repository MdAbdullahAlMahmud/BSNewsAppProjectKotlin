package com.example.mvvmbsnews.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvmbsnews.repository.NewsRepository

class NewsViewModel (val  newsRepository: NewsRepository) : ViewModel() {
}