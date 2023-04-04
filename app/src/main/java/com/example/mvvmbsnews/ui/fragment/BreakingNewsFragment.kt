package com.example.mvvmbsnews.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.adapter.NewsAdapter
import com.example.mvvmbsnews.databinding.FragmentBreakingNewsBinding
import com.example.mvvmbsnews.db.ArticleDatabase
import com.example.mvvmbsnews.repository.NewsRepository
import com.example.mvvmbsnews.ui.NewsActivity
import com.example.mvvmbsnews.ui.NewsViewModelProviderFactory
import com.example.mvvmbsnews.util.Resource
import com.example.mvvmbsnews.viewmodel.NewsViewModel


class BreakingNewsFragment : Fragment() {

    lateinit var newsViewModel :NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    lateinit var binding: FragmentBreakingNewsBinding

    val TAG = "BreakingNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       setupRecycleView()


        val newsRepository =  NewsRepository(ArticleDatabase(binding.root.context))
        val providerFactory = NewsViewModelProviderFactory(newsRepository)

        newsViewModel = ViewModelProvider(this,providerFactory).get(NewsViewModel::class.java)

       newsViewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
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



        newsAdapter.setOnNewsItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            Log.v(TAG, "Article Pojo ->  ${it.toString()}")
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,bundle)

        }


    }

    private  fun showLoading(){
        binding.progressBar.visibility = View.VISIBLE

    }

    private  fun hideLoading(){
        binding.progressBar.visibility = View.GONE
    }



    private  fun  setupRecycleView(){

        newsAdapter = NewsAdapter()

        binding.breakingNewsRV.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}