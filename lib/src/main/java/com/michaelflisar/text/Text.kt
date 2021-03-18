package com.michaelflisar.text

import android.content.Context
import android.os.Parcelable
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

    fun display(tv: TextView) {
        when (this) {
            is String -> tv.text = text
            is Resource -> tv.setText(res)
            Empty -> tv.text = ""
        }
    }
}

fun Int.asText(): Text {
    return Text.Resource(this)
}

fun String.asText(): Text {
    return Text.String(this)
}
