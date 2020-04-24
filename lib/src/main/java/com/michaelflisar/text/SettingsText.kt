package com.michaelflisar.settings.text

import android.content.Context
import android.os.Parcelable
import android.widget.TextView
import kotlinx.android.parcel.Parcelize

sealed class Text : Parcelable {

    @Parcelize
    class String(val text: kotlin.String) : Text()

    @Parcelize
    class Resource(val res: Int) : Text()

    fun get(context: Context): kotlin.String {
        return when (this) {
            is String -> text
            is Resource -> context.getString(res)
        }
    }

    fun display(tv: TextView) {
        when (this) {
            is String -> tv.text = text
            is Resource -> tv.setText(res)
        }
    }
}

fun Int.asText(): Text {
    return Text.Resource(this)
}

fun String.asText(): Text {
    return Text.String(this)
}
