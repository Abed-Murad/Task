package com.am.task.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.am.task.repo.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val photosRepository: PhotosRepository):ViewModel() {
    val showProgress = ObservableBoolean(false)
    fun getAllPhotos() = photosRepository.getPhotos()
}