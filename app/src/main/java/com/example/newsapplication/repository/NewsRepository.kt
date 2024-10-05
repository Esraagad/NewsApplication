package com.example.newsapplication.repository

import com.example.newsapplication.api.NewsAPI
import com.example.newsapplication.db.NewsDatabase
import com.example.newsapplication.models.Article
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val newsDatabase: NewsDatabase,
    private val newsAPI: NewsAPI
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        newsAPI.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        newsAPI.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) =
        newsDatabase.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article) =
        newsDatabase.getArticleDao().deleteArticle(article)

    fun getSavedNews() = newsDatabase.getArticleDao().getAllArticles()
}