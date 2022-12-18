package com.am.task.register

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.am.task.remote.model.RegisterBody
import com.am.task.remote.network.Resource
import com.am.task.repo.UserRepository
import com.am.task.validation.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val validator: Validator
) : ViewModel() {
    val showProgress = ObservableBoolean(false)
    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val age = ObservableField<String>()

    val isInputValid: ObservableBoolean = object : ObservableBoolean(email, password, age) {
        override fun get() =
            isEmailValid() && isPasswordValid() && isValidAge()
    }

    val registerBtnEnabled = object: ObservableBoolean(isInputValid, showProgress){
        override fun get(): Boolean {
            return isInputValid.get() && showProgress.get().not()
        }
    }


    fun register(): LiveData<Resource<Boolean>> {
        val email = email.get().orEmpty().trim()
        val password = password.get().orEmpty()
        val age = age.get().orEmpty()
            val body = RegisterBody(email = email, password = password, age.toInt())
            return userRepository.register(body)
    }

    fun validateForm(): Boolean {
        return isEmailValid() && isPasswordValid() && isValidAge()
    }


    fun isEmailValid() = validator.isValidEmail(email.get().orEmpty().trim())
    fun isPasswordValid() = validator.isValidPassword(password.get().orEmpty())
    fun isValidAge() = validator.isValidAge(age.get()?.toInt() ?: 0)
}