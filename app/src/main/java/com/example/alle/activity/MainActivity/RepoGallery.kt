package com.example.alle.activity.MainActivity

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.alle.LocalImageResponse
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class RepoGallery @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val _imagesLiveData: MutableStateFlow<LocalImageResponse> = MutableStateFlow(LocalImageResponse.Loading)
    val imagesLiveData: StateFlow<LocalImageResponse> = _imagesLiveData

    suspend fun loadImages() {
        val images = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val selection = "${MediaStore.Images.Media.DATA} like ? "
        val selectionArgs = arrayOf("%/DCIM/%")
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
        val cursor = Contexts.getApplication(context).contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            columns,
            selection,
            selectionArgs,
            sortOrder
        )
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                images.add(uri)
            }
        }
        _imagesLiveData.emit(LocalImageResponse.Success(images))
    }
}
