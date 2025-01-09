package com.example.gabinews.model.repository

import com.example.gabinews.model.data.NewsResponse

interface NewsRepository {

    suspend fun getTopNews(category: String) :NewsResponse

}