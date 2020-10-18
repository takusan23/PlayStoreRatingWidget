package io.github.takusan23.playstoreratingwidget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object GlideCoroutine {

    /** GlideでBitmapを取得するやつ */
    suspend fun coroutineGlideBitmap(context: Context, url: String) = suspendCoroutine<Bitmap> {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                it.resume(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })
    }

}