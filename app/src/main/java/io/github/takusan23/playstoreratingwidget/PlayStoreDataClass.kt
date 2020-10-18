package io.github.takusan23.playstoreratingwidget

/**
 * GooglePlayのHTMLスクレイピング結果データクラス
 * */
data class PlayStoreDataClass(
    val title: String,
    val iconURL: String,
    val downloadCount: String,
    val averageRating: String,
    val postCount: String,
    val ratingList: List<String>,
)