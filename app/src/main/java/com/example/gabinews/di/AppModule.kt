package com.example.gabinews.di

import com.example.gabinews.model.net.ApiService
import com.example.gabinews.model.net.createApiService
import com.example.gabinews.model.repository.news.NewsRepository
import com.example.gabinews.model.repository.news.NewsRepositoryImpl
import com.example.gabinews.ui.feature.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ApiService> { createApiService() }

    single<NewsRepository> { NewsRepositoryImpl(get()) }

    viewModel { NewsViewModel(get()) }

}