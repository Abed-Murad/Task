package com.am.task.remote.endpoints

import androidx.lifecycle.LiveData
import com.am.task.remote.model.LoginBody
import com.am.task.remote.model.RegisterBody
import com.am.task.remote.network.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountEndpoints {

    @POST("/login")
    fun login(@Body body: LoginBody): LiveData<ApiResponse<Boolean>>

    @POST("register/")
    fun register(@Body body: RegisterBody): LiveData<ApiResponse<Boolean>>

}