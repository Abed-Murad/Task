package com.am.task.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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
    val showProgressBar = MutableLiveData<Boolean>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val age = MutableLiveData<String>()
    val isInputValid = MediatorLiveData<Boolean>()
    private val isValidEmailAddress = MutableLiveData(false)
    private val isValidPassword = MutableLiveData(false)
    private val isValidAge = MutableLiveData(false)


    init {
        isInputValid.addSource(email){
            isInputValid.value = validateForm()
            isValidEmailAddress.value = isEmailValid()
        }
        isInputValid.addSource(password){
            isInputValid.value = validateForm()
            isValidPassword.value = isPasswordValid()
        }
        isInputValid.addSource(age){
            isInputValid.value = validateForm()
            isValidAge.value = isValidAge()
        }
    }



    fun register(): LiveData<Resource<Boolean>> {
        val email = email.value.orEmpty().trim()
        val password = password.value.orEmpty()
        val age = age.value.orEmpty()
            val body = RegisterBody(email = email, password = password, age.toInt())
            return userRepository.register(body)
    }

    fun validateForm(): Boolean {
        return isEmailValid() && isPasswordValid() && isValidAge()
    }


    fun isEmailValid() = validator.isValidEmail(email.value.orEmpty().trim())
    fun isPasswordValid() = validator.isValidPassword(password.value.orEmpty())
    fun isValidAge() = validator.isValidAge(age.value?.toInt() ?: 0)
}