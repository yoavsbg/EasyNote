package yoavsabag.easynote.ui.notelist

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import yoavsabag.easynote.model.Note

@BindingAdapter("hideWhenNoText")
fun TextView.setHideTitleWhenNoTexts(item: String) {
    item.let {
        visibility = when (item.isNullOrEmpty()) {
            true -> View.GONE
            else -> View.VISIBLE
        }
    }
}

