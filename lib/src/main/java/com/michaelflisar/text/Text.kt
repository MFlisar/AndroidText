package com.michaelflisar.text

import android.content.Context
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import kotlinx.parcelize.Parcelize

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

    fun getString(context: Context, vararg args: Any?): kotlin.String {
        val charSequence = get(context)
        return if (args.isNotEmpty()) {
            kotlin.String.format(charSequence.toString(), *args)
        } else charSequence.toString()
    }

    fun display(textView: TextView): kotlin.CharSequence = display(textView.context) {
        textView.text = it
    }

    fun display(
        context: Context,
        setter: (charSequence: kotlin.CharSequence) -> Unit
    ): kotlin.CharSequence {
        val charSequence = get(context)
        setter(charSequence)
        return charSequence
    }

    fun <T : View> display(
        view: T,
        setter: (view: T, charSequence: kotlin.CharSequence) -> Unit
    ): kotlin.CharSequence {
        val charSequence = get(view.context)
        setter(view, charSequence)
        return charSequence
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
