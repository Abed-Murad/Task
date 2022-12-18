package com.am.task.validation

import android.util.Patterns


interface Validator {
    fun isValidEmail(email: String): Boolean
    fun isValidPassword(password: String): Boolean
}


class ValidatorImpl : Validator {
    override fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun isValidPassword(password: String): Boolean {
        return password.length in 6..12
    }
}