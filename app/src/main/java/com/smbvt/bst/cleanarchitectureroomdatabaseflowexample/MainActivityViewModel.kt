package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.domain.usecase.PhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(@ApplicationContext context: Context, private val photosUseCase: PhotosUseCase) :
    AndroidViewModel(context as Application) {

    private val appContext = getApplication<Application>()

    sealed class UIEvent {
        data object FinishActivity : UIEvent()
        data class ShowToast(val message: String) : UIEvent()
    }

    var uiEvents = MutableSharedFlow<UIEvent>()
    private set


    fun insertPhoto(photoUrl: String) {
        viewModelScope.launch {
            photosUseCase.insertPhotoUrl(photoUrl)
            uiEvents.emit(UIEvent.ShowToast(appContext.getString(R.string.successfully_saved)))
        }
    }
}