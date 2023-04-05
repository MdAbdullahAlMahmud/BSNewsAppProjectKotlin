package com.example.mvvmbsnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.model.OnBoardITem

class OnBoardAdapter (val list : List<OnBoardITem>) :
    RecyclerView.Adapter<OnBoardAdapter.OnBoardViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.onboard_item_design,parent,false)
        return  OnBoardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: OnBoardViewHolder, position: Int) {

        var item  = list.get(position)
        holder.titleTv.text =item.title
        holder.descriptionTv.text =item.description
        holder.imageIV.setImageResource(item.image)

    }

    inner class  OnBoardViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var  titleTv = itemView.findViewById<TextView>(R.id.onBoardItemTitle)
        var  descriptionTv = itemView.findViewById<TextView>(R.id.onBoardItemDescription)
        var  imageIV = itemView.findViewById<ImageView>(R.id.onBoardItemImage)

    }
}