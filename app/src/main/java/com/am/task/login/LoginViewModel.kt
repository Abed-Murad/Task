package com.am.task.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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

    val showProgressBar = MutableLiveData<Boolean>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isInputValid = MediatorLiveData<Boolean>()
    val isValidEmailAddress = MutableLiveData(false)
    val isValidPassword = MutableLiveData(false)


    init {
        isInputValid.addSource(email){
            isInputValid.value = validateEmailAndPassword()
            isValidEmailAddress.value = isEmailValid()
        }
        isInputValid.addSource(password){
            isInputValid.value = validateEmailAndPassword()
            isValidPassword.value = isPasswordValid()
        }
    }



    fun login(): LiveData<Resource<Boolean>> {
        val email = email.value.orEmpty().trim()
        val password = password.value.orEmpty()
            val body = LoginBody(email = email, password = password)
            return userRepository.login(body)
    }

    private fun validateEmailAndPassword(): Boolean {
        return isEmailValid() && isPasswordValid()
    }


    fun isEmailValid() = validator.isValidEmail(email.value.orEmpty().trim())
    fun isPasswordValid() = validator.isValidPassword(password.value.orEmpty())
}