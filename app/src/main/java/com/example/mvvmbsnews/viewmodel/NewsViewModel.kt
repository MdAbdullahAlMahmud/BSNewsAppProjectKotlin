package com.example.mvvmbsnews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmbsnews.model.NewsResponse
import com.example.mvvmbsnews.repository.NewsRepository
import com.example.mvvmbsnews.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel (val  newsRepository: NewsRepository) : ViewModel() {


    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    var breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }

    fun  getBreakingNews(countryCode :String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)

        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) :Resource<NewsResponse>{

        if (response.isSuccessful){
            response.body()?.let {result->
                return  Resource.Success(result)
            }
        }

        return  Resource.Error(response.message())

    }


}