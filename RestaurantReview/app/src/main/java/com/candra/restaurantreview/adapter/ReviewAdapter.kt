package com.candra.restaurantreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candra.restaurantreview.databinding.ItemReviewBinding

class ReviewAdapter(private val list: List<String>): RecyclerView.Adapter<ReviewAdapter.ViewHolder>()
{

    class ViewHolder(private val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: String){
            with(binding){
                tvItems.text = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        return ViewHolder(
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}