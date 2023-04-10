package com.example.mvvmbsnews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmbsnews.adapter.NewsAdapter
import com.example.mvvmbsnews.databinding.FragmentSavedNewsBinding
import com.example.mvvmbsnews.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment () {

    lateinit var newsViewModel : NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    lateinit var binding: FragmentSavedNewsBinding

    val TAG = "SavedNewsFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        return  binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        newsViewModel.getAllArticle().observe(viewLifecycleOwner, Observer {articles->
            newsAdapter.differ.submitList(articles)

        })

        val itemTouchHelperCallback = object  :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]

                newsViewModel.deleteArticle(article)

                Snackbar.make(view,"Deleted Successfully",Snackbar.LENGTH_LONG).apply {

                    setAction("Undo"){
                        newsViewModel.saveArticle(article)
                    }
                    show()
                }

            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
        }


        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedNewsRV)
        }


    }

    private  fun  setupRecycleView(){

        newsAdapter = NewsAdapter()

        binding.savedNewsRV.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }


}