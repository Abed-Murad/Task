package com.am.task.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visible")
    fun viewVisible(view: View, visible: Boolean) {
        if (visible)
            view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }
}