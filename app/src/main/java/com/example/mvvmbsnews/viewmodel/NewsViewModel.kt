package com.example.mvvmbsnews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmbsnews.model.Article
import com.example.mvvmbsnews.model.NewsResponse
import com.example.mvvmbsnews.repository.NewsRepository
import com.example.mvvmbsnews.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (val  newsRepository: NewsRepository) : ViewModel() {


    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse : NewsResponse? =null

    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse : NewsResponse? =null


    init {
        getBreakingNews("us")
    }

    fun  getBreakingNews(countryCode :String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)

        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun  searchNews(query :String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(query,searchNewsPage)

        searchNews.postValue(handleSearchingNewsResponse(response))
    }




    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) :Resource<NewsResponse>{

        if (response.isSuccessful){
            response.body()?.let {result->

                breakingNewsPage ++
                if (breakingNewsResponse ==null){
                    //means first time
                    breakingNewsResponse = result
                }else{
                    val oldArticle = breakingNewsResponse?.articles
                    val newArticle = result.articles
                    oldArticle?.addAll(newArticle)
                }

                return  Resource.Success(breakingNewsResponse?:result)
            }
        }

        return  Resource.Error(response.message())

    }

     private fun handleSearchingNewsResponse(response: Response<NewsResponse>) :Resource<NewsResponse>{

            if (response.isSuccessful){
                response.body()?.let {result->


                    searchNewsPage ++
                    if (searchNewsResponse ==null){
                        //means first time
                        searchNewsResponse = result
                    }else{
                        val oldArticle = searchNewsResponse?.articles
                        val newArticle = result.articles
                        oldArticle?.addAll(newArticle)
                    }

                    return  Resource.Success(searchNewsResponse?:result)

                }
            }

            return  Resource.Error(response.message())

        }


    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsertArticle(article)
    }


    fun getAllArticle() = newsRepository.getAllArticleFromDB()

    fun  deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticleItem(article)
    }



}