package io.github.takusan23.playstoreratingwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Implementation of App Widget functionality.
 */
class RatingWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateRatingWidget(context)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

/** Widgetを更新する関数 */
fun updateRatingWidget(context: Context) {
    // データ取得
    GlobalScope.launch {
        // パッケージ名
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        // Contextあれば更新できる！
        val componentName = ComponentName(context, RatingWidget::class.java)
        val manager = AppWidgetManager.getInstance(context)
        val ids = manager.getAppWidgetIds(componentName)
        val data = PlayStoreHTMLParser().getPlayStoreData(preference.getString("package_name", null) ?: return@launch)
        // アイコン取りに行く
        val iconBitmap = GlideCoroutine.coroutineGlideBitmap(context, data.iconURL)
        // Widget更新する
        ids.forEach { appWidgetId ->
            val view = RemoteViews(context.packageName, R.layout.rating_widget)
            view.apply {
                setImageViewBitmap(R.id.widget_rating_icon_imageview, iconBitmap)
                setTextViewText(R.id.widget_rating_title_textview, data.title)
                setTextViewText(R.id.widget_rating_download_textview, data.downloadCount)
                setTextViewText(R.id.widget_rating_average_textview, data.averageRating)
                setTextViewText(R.id.widget_rating_total_post_textview, "${data.postCount}件")
                setProgressBar(R.id.widget_rating_progress_5, 100, data.ratingList[0].toInt(), false)
                setProgressBar(R.id.widget_rating_progress_4, 100, data.ratingList[1].toInt(), false)
                setProgressBar(R.id.widget_rating_progress_3, 100, data.ratingList[2].toInt(), false)
                setProgressBar(R.id.widget_rating_progress_2, 100, data.ratingList[3].toInt(), false)
                setProgressBar(R.id.widget_rating_progress_1, 100, data.ratingList[4].toInt(), false)
            }
            manager.updateAppWidget(appWidgetId, view)
        }
    }

}