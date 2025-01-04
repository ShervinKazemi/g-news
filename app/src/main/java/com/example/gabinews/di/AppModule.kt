package com.example.gabinews.di

import com.example.gabinews.model.net.ApiService
import com.example.gabinews.model.net.createApiService
import org.koin.dsl.module
import org.koin.dsl.single

val appModule = module {

    single<ApiService> { createApiService() }



}