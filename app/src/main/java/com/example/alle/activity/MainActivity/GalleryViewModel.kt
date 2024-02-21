package com.example.alle.activity.MainActivity

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.alle.LocalImageResponse
import com.example.alle.datastructure.FixedLimitStack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: RepoGallery
) : ViewModel() {

    val imagesLiveData: StateFlow<LocalImageResponse>
        get() = repository.imagesLiveData

    private val _selectedImage: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    val selectedImage: StateFlow<Uri> = _selectedImage


    val fixedLimitStack = FixedLimitStack<Uri, Painter?>(10)

    fun permissionGrantedLoadImage(b: Boolean) {
        if (b) viewModelScope.launch {
            repository.loadImages()
        }
    }

    fun setSelectedImage(uri: Uri) {
        viewModelScope.launch {
            _selectedImage.emit(uri)
        }
    }

}