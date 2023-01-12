package com.candra.latihannetworkingwithloopj

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candra.latihannetworkingwithloopj.databinding.ItemQuoteBinding

class QuoteAdapter(private val listReview: ArrayList<String>): RecyclerView.Adapter<QuoteAdapter.ViewHolder>(){

    class ViewHolder(private val binding: ItemQuoteBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: String){
            with(binding){
                tvItem.text = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteAdapter.ViewHolder {
        return ViewHolder(
            ItemQuoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: QuoteAdapter.ViewHolder, position: Int) {
       holder.bind(data = listReview[position])
    }

    override fun getItemCount(): Int = listReview.size

}