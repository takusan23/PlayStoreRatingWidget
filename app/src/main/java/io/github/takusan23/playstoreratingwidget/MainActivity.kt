package io.github.takusan23.playstoreratingwidget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 保存する
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        packagename_exittext.setText(preference.getString("package_name", ""))

        register_button.setOnClickListener {
            val packageName = packagename_exittext.text.toString()
            preference.edit {
                putString("package_name", packageName)
            }
            updateRatingWidget(this)
        }

    }
}