package com.example.gabinews.model.repository

import com.example.gabinews.model.data.NewsResponse
import com.example.gabinews.model.net.ApiService

class NewsRepositoryImpl(private val apiService: ApiService): NewsRepository {

    override suspend fun getTopNews(category: String): NewsResponse {
        return apiService.getTopNews(category)
    }

}