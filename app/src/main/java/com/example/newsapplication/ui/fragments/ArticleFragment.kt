package com.example.newsapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.newsapplication.databinding.FragmentArticleBinding
import com.example.newsapplication.models.Article
import com.example.newsapplication.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val args: ArticleFragmentArgs by navArgs()
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var article: Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article = args.article

        loadNewsWebView()
        setFabClickListener()
    }

    private fun loadNewsWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
    }

    private fun setFabClickListener() {
        binding.fab.setOnClickListener { view ->
            newsViewModel.saveArticle(article)
            view?.let { Snackbar.make(it, "Article saved!", Snackbar.LENGTH_SHORT).show() }
        }
    }
}