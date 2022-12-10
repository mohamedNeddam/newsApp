package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.ArticleDataBase

class NewsRepository(
    val db: ArticleDataBase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String,pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery,pageNumber)
}