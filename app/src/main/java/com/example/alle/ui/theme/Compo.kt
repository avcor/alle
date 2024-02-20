package com.example.alle.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alle.activity.MainActivity.GalleryViewModel


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val galleryViewModel: GalleryViewModel = viewModel()
    val images = galleryViewModel.imagesLiveData.collectAsState()
    val list = listOf<String>("hey", "hello")
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        LazyRow {
            items(list.size) { item ->
                Text(text = list[item], modifier = Modifier.padding(16.dp))
            }
        }
    }
}