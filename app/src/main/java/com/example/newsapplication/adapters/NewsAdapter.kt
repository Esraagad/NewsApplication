package com.example.newsapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ItemArticlePreviewBinding
import com.example.newsapplication.models.Article
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

     val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemArticlePreviewBinding = ItemArticlePreviewBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(itemArticlePreviewBinding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article?) {

            article?.let {
                binding.apply {
                    Glide.with(binding.root.context).load(article.urlToImage).into(ivArticleImage)
                    tvSource.text = article.source.name
                    tvDescription.text = article.description
                    tvTitle.text = article.title
                    tvPublishedAt.text = article.publishedAt
                    root.setOnClickListener {
                        onItemClickListener?.let { it(article) }
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}