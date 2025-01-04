package com.example.gabinews.model.repository.home

import com.example.gabinews.model.data.NewsResponse
import com.example.gabinews.model.net.ApiService

class HomeRepositoryImpl(val apiService: ApiService): HomeRepository {

    override suspend fun getTopNews(category: String): NewsResponse {
        return apiService.getTopNews(category)
    }

}