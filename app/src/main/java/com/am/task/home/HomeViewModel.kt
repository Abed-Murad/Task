package com.am.task.home

import androidx.lifecycle.ViewModel
import com.am.task.repo.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val photosRepository: PhotosRepository):ViewModel() {


    fun getAllPhotos() = photosRepository.getPhotos()
}