package com.example.mvvmbsnews.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.adapter.NewsAdapter
import com.example.mvvmbsnews.databinding.FragmentBreakingNewsBinding
import com.example.mvvmbsnews.databinding.FragmentSearchNewsBinding
import com.example.mvvmbsnews.db.ArticleDatabase
import com.example.mvvmbsnews.repository.NewsRepository
import com.example.mvvmbsnews.ui.NewsViewModelProviderFactory
import com.example.mvvmbsnews.util.Constant.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.mvvmbsnews.util.Resource
import com.example.mvvmbsnews.viewmodel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {


    lateinit var newsViewModel : NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    lateinit var binding: FragmentSearchNewsBinding

    val TAG = "SearchNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()


        val newsRepository =  NewsRepository(ArticleDatabase(binding.root.context))
        val providerFactory = NewsViewModelProviderFactory(newsRepository)

        newsViewModel = ViewModelProvider(this,providerFactory).get(NewsViewModel::class.java)


        var job: Job? = null

        binding.searchEDT.addTextChangedListener {editable->
        job?.cancel()

            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        newsViewModel.searchNews(editable.toString())
                    }
                }
            }

        }




        newsViewModel.searchNews.observe(viewLifecycleOwner, Observer {response->
            when(response){

                is  Resource.Success ->{
                    hideLoading()
                    response.data?.let {result->
                        newsAdapter.differ.submitList(result.articles)
                    }

                }

                is Resource.Error->{
                    hideLoading()
                    response.message?.let {message->

                        Log.e(TAG,"An error occured $message")
                    }
                }

                is  Resource.Loading->{
                    showLoading()
                }
            }

        })

    }

    private  fun showLoading(){
        binding.searchProgressBar.visibility = View.VISIBLE

    }

    private  fun hideLoading(){
        binding.searchProgressBar.visibility = View.GONE
    }



    private  fun  setupRecycleView(){

        newsAdapter = NewsAdapter()

        binding.searchRV.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}