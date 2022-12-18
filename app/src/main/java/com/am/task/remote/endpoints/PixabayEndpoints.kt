package com.am.task.remote.endpoints

import androidx.lifecycle.LiveData
import com.am.task.ApiConstants
import com.am.task.remote.model.PhotoListDTO
import com.am.task.remote.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayEndpoints {
    @GET("api/")
    fun getPhotosList(
        @Query("key") key: String = ApiConstants.PIXABAY_KEY,
        @Query("q") query: String = "cats",
        @Query("image_type") imageType: String = ApiConstants.IMAGE_TYPE,
    ): LiveData<ApiResponse<PhotoListDTO>>
}