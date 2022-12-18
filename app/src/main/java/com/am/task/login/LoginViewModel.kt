package com.am.task.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.am.task.remote.model.LoginBody
import com.am.task.remote.network.Resource
import com.am.task.repo.UserRepository
import com.am.task.validation.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val validator: Validator
) : ViewModel() {

    val showProgress = ObservableBoolean(false)
    val email = ObservableField<String>()
    val password = ObservableField<String>()


    val isInputValid: ObservableBoolean = object : ObservableBoolean(email, password) {
        override fun get() =
            isEmailValid() && isPasswordValid()
    }

    val loginBtnEnabled = object: ObservableBoolean(isInputValid, showProgress){
        override fun get(): Boolean {
            return isInputValid.get() && showProgress.get().not()
        }
    }


    fun login(): LiveData<Resource<Boolean>> {
        val email = email.get().orEmpty().trim()
        val password = password.get().orEmpty()
            val body = LoginBody(email = email, password = password)
            return userRepository.login(body)
    }


    fun isEmailValid() = validator.isValidEmail(email.get().orEmpty().trim())
    fun isPasswordValid() = validator.isValidPassword(password.get().orEmpty())
}