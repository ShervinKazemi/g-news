package com.example.gabinews.di

import com.example.gabinews.model.net.ApiService
import com.example.gabinews.model.net.createApiService
import com.example.gabinews.model.repository.home.HomeRepository
import com.example.gabinews.model.repository.home.HomeRepositoryImpl
import org.koin.dsl.module

val appModule = module {

    single<ApiService> { createApiService() }

    single<HomeRepository> { HomeRepositoryImpl(get()) }


}