package com.example.mvvmbsnews.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.databinding.FragmentArticleBinding
import com.example.mvvmbsnews.databinding.FragmentBreakingNewsBinding
import com.example.mvvmbsnews.db.ArticleDatabase
import com.example.mvvmbsnews.model.Article
import com.example.mvvmbsnews.repository.NewsRepository
import com.example.mvvmbsnews.ui.NewsActivity
import com.example.mvvmbsnews.ui.NewsViewModelProviderFactory
import com.example.mvvmbsnews.util.CustomWebClient
import com.example.mvvmbsnews.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment() {

    lateinit var  binding: FragmentArticleBinding
    lateinit var  article:Article
    lateinit var destinationUrl :String

    val args : ArticleFragmentArgs by navArgs()

    val TAG = "ArticleFragment"
    lateinit var newsViewModel : NewsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val newsRepository =  NewsRepository(ArticleDatabase(binding.root.context))
        val providerFactory = NewsViewModelProviderFactory(newsRepository)

        newsViewModel = ViewModelProvider(this,providerFactory)[NewsViewModel::class.java]

        val article = args.article

        Log.v(TAG, "Args Article -> ${article.toString()}")

        binding.webView.apply {
            webViewClient = CustomWebClient(binding.webViewProgressBar)
            article.url?.let { loadUrl(it) }

        }

        binding.fab.setOnClickListener{
            newsViewModel.saveArticle(article)
            Snackbar.make(view,"Article saved successfully",Toast.LENGTH_SHORT).show()
        }


    }


}