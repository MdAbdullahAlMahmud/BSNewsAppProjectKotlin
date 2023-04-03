package com.example.mvvmbsnews.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmbsnews.adapter.NewsAdapter
import com.example.mvvmbsnews.databinding.FragmentBreakingNewsBinding
import com.example.mvvmbsnews.util.Resource
import com.example.mvvmbsnews.viewmodel.NewsViewModel


class BreakingNewsFragment : Fragment() {


    lateinit var  newsViewModel : NewsViewModel
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
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
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        newsViewModel = (activity as NewsActivity).viewModel
        setupRecycleView()


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