package com.am.task.repo

import androidx.lifecycle.LiveData
import com.am.task.remote.endpoints.PixabayEndpoints
import com.am.task.remote.model.PhotoListDTO
import com.am.task.remote.network.ApiResponse
import com.am.task.remote.network.NetworkBoundResourceNoCache
import com.am.task.remote.network.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(private val pixabayEndpoints: PixabayEndpoints) {

    fun getPhotos(): LiveData<Resource<PhotoListDTO>> {
        return object : NetworkBoundResourceNoCache<PhotoListDTO>() {
            override fun createCall(): LiveData<ApiResponse<PhotoListDTO>> {
                return pixabayEndpoints.getPhotosList()
            }
        }.asLiveData()
    }


}