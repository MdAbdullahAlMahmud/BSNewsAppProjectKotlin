package com.example.mvvmbsnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.databinding.ItemArticlePreviewBinding
import com.example.mvvmbsnews.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {

        return  NewViewHolder(ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {

        var article = differ.currentList.get(position)

        holder.itemView.apply {
            Glide.with(context).load(article.urlToImage).into(holder.binding.ivArticleImage)
            holder.binding.tvSource.text = article.source?.name
            holder.binding.tvTitle.text = article.title
            holder.binding.tvDescription.text = article.description
            holder.binding.tvPublishedAt.text = article.publishedAt

            setOnClickListener {
                onNewsItemClickListener?.let {
                    it(article)
                }
            }


        }


    }

    private  var onNewsItemClickListener :((Article)->Unit)? = null

    fun setOnNewsItemClickListener(listener : (Article)->Unit){
        onNewsItemClickListener = listener
    }



    private  val  differCallback =object:DiffUtil.ItemCallback<Article>(){

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return  oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return  oldItem == newItem
        }
    }

    val differ  = AsyncListDiffer(this,differCallback)


    inner  class  NewViewHolder(val binding: ItemArticlePreviewBinding) : RecyclerView.ViewHolder(binding.root)

}