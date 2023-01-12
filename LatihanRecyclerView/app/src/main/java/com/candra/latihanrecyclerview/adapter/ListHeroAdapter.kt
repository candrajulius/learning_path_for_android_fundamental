package com.candra.latihanrecyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.candra.latihanrecyclerview.R
import com.candra.latihanrecyclerview.model.Hero

class ListHeroAdapter(private val listHero: ArrayList<Hero>) : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
           var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
           var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
           var tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListHeroAdapter.ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_hero,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ListHeroAdapter.ListViewHolder, position: Int) {
        val(name,description,photo) = listHero[position]
        holder.tvName.text = name
        holder.tvDescription.text = description
        Glide.with(holder.itemView.context)
            .load(photo) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(holder.imgPhoto) // imageview mana yang akan diterpkan
        holder.itemView.setOnClickListener {
           onItemClickCallback.onItemClicked(listHero[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: Hero)
    }

    override fun getItemCount(): Int = listHero.size
}
/*
Beberapa fungsi lain dari glide
    1. transition => digunakan untuk menambahkan transisi ketika gambar selesai dimuat
    2. thumbnail => digunakan untuk menambahkan thumbnail atau gambar sebelum gambar dimuat
    3. erro => digunakan untuk menggantikan gambar yang gagal ketika dimuat
 */