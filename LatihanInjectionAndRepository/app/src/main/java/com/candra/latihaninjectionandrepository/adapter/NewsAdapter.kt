package com.candra.latihaninjectionandrepository.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.candra.latihaninjectionandrepository.R
import com.candra.latihaninjectionandrepository.databinding.ItemNewsBinding
import com.candra.latihaninjectionandrepository.entity.NewsEntity
import com.candra.latihaninjectionandrepository.helper.DateFormatter


class NewsAdapter(private val onBookmarkClick: (NewsEntity) -> Unit) : ListAdapter<NewsEntity,MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(
           ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)

        holder.bindData(news)

        val ivBookmark = holder.binding.ivBookmark
        if (news.isBookmarked){
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context,R.drawable.ic_bookmarked_white))
        }else{
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context,R.drawable.ic_bookmark_white))
        }

        ivBookmark.setOnClickListener {
            onBookmarkClick(news)
        }
    }

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>(){
                override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                   return oldItem == newItem
                }

            }
    }

}


class MyViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root){
    fun bindData(news: NewsEntity)
    {
        with(binding){
            tvItemTitle.text = news.title
            tvItemPublishedDate.text = DateFormatter.formatDate(news.publishedAt)
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(imgPoster)
            itemView.setOnClickListener {
                itemView.context.startActivity(
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(news.url)
                    }
                )
            }
        }
    }
}
