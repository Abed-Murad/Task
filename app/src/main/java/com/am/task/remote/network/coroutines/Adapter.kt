package com.am.task.remote.network.coroutines

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.am.task.remote.network.Resource
import com.am.task.remote.network.Status
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


suspend fun <T> LiveData<Resource<T>>.toResult(lifeCycleOwner: LifecycleOwner): Resource<T> {
    return suspendCoroutine { continuation ->
        this.observe(lifeCycleOwner, object: Observer<Resource<T>> {
            override fun onChanged(resource: Resource<T>) {
                when (resource.status) {
                    Status.SUCCESS, Status.ERROR -> {
                        continuation.resume(resource)
                        this@toResult.removeObserver(this)
                    }
                    Status.LOADING -> {
                        // just wait for result
                    }
                }
            }
        })
    }
}