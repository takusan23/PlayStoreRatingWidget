package io.github.takusan23.playstoreratingwidget

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class PlayStoreHTMLParser {

    /**
     * PlayStoreのHTMLを解析して、[PlayStoreDataClass]に入れて返す関数。コルーチンで使ってね
     * @param packageName パッケージ名。かぶることはない
     * */
    suspend fun getPlayStoreData(packageName: String) = withContext(Dispatchers.IO) {
        // 同期処理
        val document = Jsoup.connect("https://play.google.com/store/apps/details?id=$packageName").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36").execute().parse()
        val title = document.getElementsByClass("AHFaub")[0].text()
        val iconURL = document.getElementsByClass("xSyT2c")[0].getElementsByTag("img")[0].attr("src")
        val downloadCount = document.getElementsByClass("htlgb")[5].text()
        val averageRating = document.getElementsByClass("BHMmbe")[0].text()
        val postCount = document.getElementsByClass("AYi5wd TBRnV")[0].text()
        val ratingList = document.getElementsByClass("L2o20d").map { element ->
            element.attr("style").replace("width: ","").replace("%","")
        }
        PlayStoreDataClass(title, iconURL, downloadCount, averageRating, postCount, ratingList)
    }

}