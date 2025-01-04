package com.example.gabinews.model.data


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
) : Parcelable {
    @Parcelize
    data class Article(
        @SerializedName("author")
        val author: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("publishedAt")
        val publishedAt: String,
        @SerializedName("source")
        val source: Source,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("urlToImage")
        val urlToImage: String
    ) : Parcelable {
        @Parcelize
        data class Source(
            @SerializedName("id")
            val id: String,
            @SerializedName("name")
            val name: String
        ) : Parcelable
    }
}