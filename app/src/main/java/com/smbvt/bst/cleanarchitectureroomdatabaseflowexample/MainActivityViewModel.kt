package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.domain.usecase.PhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val photosUseCase: PhotosUseCase) :
    ViewModel() {
    fun insertPhoto(photoUrl: String) {
        viewModelScope.launch {
            photosUseCase.insertPhotoUrl(photoUrl)
        }
    }
}