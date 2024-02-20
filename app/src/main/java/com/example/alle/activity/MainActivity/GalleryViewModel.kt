package com.example.alle.activity.MainActivity

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alle.LocalImageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GalleryViewModel  @Inject constructor (
    private val repository: RepoGallery
): ViewModel() {

    val imagesLiveData: StateFlow<LocalImageResponse>
        get() = repository.imagesLiveData

    private val _selectedImage: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    val selectedImage: StateFlow<Uri> = _selectedImage


    fun permissionGrantedLoadImage(b: Boolean){
        if(b) viewModelScope.launch{
            repository.loadImages()
        }
    }

    fun setSelectedImage(uri: Uri) {
        viewModelScope.launch {
            _selectedImage.emit(uri)
        }
    }

}