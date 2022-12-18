package com.am.task.repo

import androidx.lifecycle.LiveData
import com.am.task.remote.endpoints.AccountEndpoints
import com.am.task.remote.model.LoginBody
import com.am.task.remote.model.RegisterBody
import com.am.task.remote.network.ApiResponse
import com.am.task.remote.network.NetworkBoundResourceNoCache
import com.am.task.remote.network.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository  @Inject constructor(private val accountEndpoints: AccountEndpoints) {

    fun login(body: LoginBody): LiveData<Resource<Boolean>> {
        return object : NetworkBoundResourceNoCache<Boolean>() {
            override fun createCall(): LiveData<ApiResponse<Boolean>> {
                return accountEndpoints.login(body)
            }
        }.asLiveData()
    }

    fun register(body: RegisterBody): LiveData<Resource<Boolean>> {
        return object : NetworkBoundResourceNoCache<Boolean>() {
            override fun createCall(): LiveData<ApiResponse<Boolean>> {
                return accountEndpoints.register(body)
            }

        }.asLiveData()
    }

}