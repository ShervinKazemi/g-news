package com.example.gabinews.ui.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gabinews.model.data.NewsResponse
import com.example.gabinews.model.repository.news.NewsRepository
import com.example.gabinews.util.UiState
import com.example.gabinews.util.coroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) :ViewModel() {
    private val _news = MutableStateFlow<UiState<List<NewsResponse.Article>>>(UiState.Loading)
    val news :StateFlow<UiState<List<NewsResponse.Article>>> = _news

    fun getNewsByCategory(category:String) {

        viewModelScope.launch(coroutineExceptionHandler) {
            _news.value = UiState.Loading
            try {
                val response = newsRepository.getTopNews(category)
                _news.value = UiState.Success(response.articles)
            } catch (e : Exception) {
                _news.value = UiState.Error(e.message.toString())
            }
        }

    }

}