package com.example.alle

import android.net.Uri

sealed interface LocalImageResponse{
    object Loading : LocalImageResponse

    object Failure: LocalImageResponse

    data class Success (val list : List<Uri>): LocalImageResponse
}