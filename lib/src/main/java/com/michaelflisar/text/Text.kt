package com.michaelflisar.text

import android.content.Context
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import kotlinx.android.parcel.Parcelize

sealed class Text : Parcelable {

    @Parcelize
    class String(val text: kotlin.String) : Text()

    @Parcelize
    class Resource(val res: Int) : Text()

    @Parcelize
    object Empty : Text()

    fun get(context: Context): kotlin.String {
        return when (this) {
            is String -> text
            is Resource -> context.getString(res)
            Empty -> ""
        }
    }

    fun display(tv: TextView, visibilityEmpty: Int? = null, vararg args: Any?) {
        var text = get(tv.context)
        if (args.isNotEmpty()) {
            text = kotlin.String.format(text, *args)
        }
        tv.text = text
        visibilityEmpty?.let {
            tv.visibility = if (text.isEmpty()) it else View.VISIBLE
        }
    }
}

fun Int.asText(): Text {
    return Text.Resource(this)
}

fun String.asText(): Text {
    return Text.String(this)
}
