package com.example.mvvmbsnews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.databinding.FragmentArticleBinding
import com.example.mvvmbsnews.databinding.FragmentBreakingNewsBinding
import com.example.mvvmbsnews.model.Article
import com.example.mvvmbsnews.ui.NewsActivity
import com.example.mvvmbsnews.util.CustomWebClient
import com.example.mvvmbsnews.viewmodel.NewsViewModel

class ArticleFragment : Fragment() {

    lateinit var  binding: FragmentArticleBinding
    lateinit var  article:Article
    lateinit var destinationUrl :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            destinationUrl = it.getString("url","NONE")

        }
    }
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


        binding.webView.apply {
            webViewClient = CustomWebClient(binding.webViewProgressBar)
           loadUrl(destinationUrl)

        }


    }


}