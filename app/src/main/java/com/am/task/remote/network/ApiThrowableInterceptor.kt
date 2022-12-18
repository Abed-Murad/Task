package com.am.task.remote.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiThrowableInterceptor @Inject constructor() : ThrowableInterceptor {
    override fun intercept(throwable: Throwable) {
    }
}