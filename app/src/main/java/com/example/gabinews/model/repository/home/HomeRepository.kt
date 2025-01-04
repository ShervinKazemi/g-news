package com.example.gabinews.model.repository.home

import com.example.gabinews.model.data.NewsResponse

interface HomeRepository {

    suspend fun getTopNews(category: String) :NewsResponse

}