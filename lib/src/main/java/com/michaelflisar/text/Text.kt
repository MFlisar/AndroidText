package com.michaelflisar.text

import android.content.Context
import android.os.Parcelable
import android.text.Spannable
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import kotlinx.android.parcel.Parcelize

sealed class Text : Parcelable {

    abstract fun isEmpty(context: Context): Boolean

    @Parcelize
    class String(val text: kotlin.String) : Text() {
        override fun isEmpty(context: Context) = text.isEmpty()
    }

    @Parcelize
    class CharSequence(val text: kotlin.CharSequence) : Text() {
        override fun isEmpty(context: Context) = text.isEmpty()
    }

    @Parcelize
    class Resource(val res: Int) : Text() {
        override fun isEmpty(context: Context) = context.getString(res).isEmpty()
    }

    @Parcelize
    object Empty : Text() {
        override fun isEmpty(context: Context) = true
    }

    fun get(context: Context): kotlin.CharSequence {

        return when (this) {
            is String -> text
            is Resource -> context.getString(res)
            is CharSequence -> text
            Empty -> ""
        }
    }

    fun display(textView: TextView) = display(textView.context) {
        textView.text = it
    }

    fun display(context: Context, setter: (charSequence: kotlin.CharSequence) -> Unit) {
        val charSequence = get(context)
        setter(charSequence)
    }

    fun display(view: View, setter: (charSequence: kotlin.CharSequence) -> Unit) {
        val charSequence = get(view.context)
        setter(charSequence)
    }
}

fun Int.asText(): Text {
    return Text.Resource(this)
}

fun String.asText(): Text {
    return Text.String(this)
}

fun CharSequence.asText(): Text {
    return Text.CharSequence(this)
}
