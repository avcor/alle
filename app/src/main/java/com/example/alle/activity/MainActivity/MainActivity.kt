@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.alle.activity.MainActivity

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alle.ui.theme.AlleTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.alle.LocalImageResponse
import com.example.alle.component.CarouselList
import com.example.alle.component.LoadingText
import com.example.alle.component.SelectedImage
import com.example.alle.permission.ui.RequestMyPermission
import com.google.accompanist.permissions.ExperimentalPermissionsApi



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContent {
            AlleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val galleryViewModel: GalleryViewModel = viewModel()
                    val imageStateList = galleryViewModel.imagesLiveData.collectAsState()
                    val selectedImage = galleryViewModel.selectedImage.collectAsState()
                    var imagesList: List<Uri> = listOf()
                    if(imageStateList.value is LocalImageResponse.Success){
                        imagesList = (imageStateList.value as LocalImageResponse.Success).list
                    }

                    SelectedImage(
                        selectedImage.value,

                    )

                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(bottom = 18.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        CarouselList(imagesList) { num ->
                            galleryViewModel.setSelectedImage(imagesList[num])
                        }
                    }

                    RequestMyPermission {
                        galleryViewModel.permissionGrantedLoadImage(it)
                    }

                    LoadingText(loading = (imageStateList.value is LocalImageResponse.Loading))
                }
            }
        }
    }
}





