package com.am.task.binding_adapter

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.am.task.R
import com.google.android.material.textfield.TextInputLayout

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visible")
    fun viewVisible(view: View, visible: Boolean) {
        if (visible)
            view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }



    @JvmStatic
    @BindingAdapter("error")
    fun error(view: TextInputLayout, message: CharSequence?){
        val errorColor = ContextCompat.getColor(view.context, R.color.error)
        val defaultTextColor =  ContextCompat.getColor(view.context, R.color.on_surface_line)
        if(message.isNullOrBlank().not()){
            view.defaultHintTextColor = ColorStateList.valueOf(errorColor)
            view.hintTextColor = ColorStateList.valueOf(errorColor)
            view.isErrorEnabled = true
            view.error =message
        }else {
            view.defaultHintTextColor = ColorStateList.valueOf(defaultTextColor)
            view.hintTextColor = ColorStateList.valueOf(defaultTextColor)
            view.isErrorEnabled = false
        }
    }

}