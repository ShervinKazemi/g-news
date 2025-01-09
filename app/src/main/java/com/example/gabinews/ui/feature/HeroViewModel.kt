package com.example.gabinews.ui.feature

import androidx.lifecycle.ViewModel
import com.example.gabinews.model.data.NewsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HeroViewModel : ViewModel() {
    private val _currentArticle = MutableStateFlow<NewsResponse.Article?>(null)
    val currentArticle: StateFlow<NewsResponse.Article?> = _currentArticle.asStateFlow()

    fun startHeroAnimation(article: NewsResponse.Article) {
        _currentArticle.value = article
    }

    fun endHeroAnimation() {
        _currentArticle.value = null
    }
}
