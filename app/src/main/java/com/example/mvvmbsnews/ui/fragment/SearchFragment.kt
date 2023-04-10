package com.example.mvvmbsnews.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.adapter.NewsAdapter
import com.example.mvvmbsnews.databinding.FragmentSearchNewsBinding
import com.example.mvvmbsnews.util.Constant
import com.example.mvvmbsnews.util.Constant.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.mvvmbsnews.util.Resource
import com.example.mvvmbsnews.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
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

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)


        var job: Job? = null

        binding.searchEDT.addTextChangedListener {editable->
        job?.cancel()

            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        newsViewModel.searchNews(editable.toString())
                    }else{

                    }
                }
            }

        }




        newsViewModel.searchNews.observe(viewLifecycleOwner, Observer {response->
            when(response){

                is  Resource.Success ->{
                    hideLoading()
                    response.data?.let {result->
                        newsAdapter.differ.submitList(result.articles.toList())
                        val totalPages = result.totalResults / Constant.QUERY_PAGE_SIZE + 2
                        isLastPage = newsViewModel.breakingNewsPage == totalPages
                        if (isLastPage){
                            binding.searchRV.setPadding(0, 0, 0, 0)
                        }
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
            findNavController().navigate(R.id.action_searchFragment_to_articleFragment,bundle)

        }


    }

    private  fun showLoading(){
        binding.searchProgressBar.visibility = View.VISIBLE
        isLoading = true

    }

    private  fun hideLoading(){
        binding.searchProgressBar.visibility = View.GONE
        isLoading = false

    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val myScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constant.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                newsViewModel.getBreakingNews("us")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private  fun  setupRecycleView(){

        newsAdapter = NewsAdapter()

        binding.searchRV.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(myScrollListener)
        }

    }
}