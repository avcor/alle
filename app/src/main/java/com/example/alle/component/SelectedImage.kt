package com.example.alle.component

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalPermissionsApi::class)
@Composable
fun SelectedImage(uri: Uri) {



    GlideImage(
        model = uri,
        contentDescription = "des",
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.Black)
    )

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LoadingText(loading: Boolean) {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    if (loading && permissionState.status.isGranted) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading ...")
        }
    }
}