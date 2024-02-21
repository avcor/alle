package com.example.alle.component

import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.FutureTarget
import com.example.alle.datastructure.FixedLimitStack
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalPermissionsApi::class)
@Composable
fun SelectedImage(uri: Uri, preBuildMap: FixedLimitStack<Uri, Painter?>) {
    val context  = LocalContext.current

    val request = ImageRequest.Builder(context)
        .data(uri)
        .build()
    val painter = rememberAsyncImagePainter(request)
    Image(
        painter = preBuildMap.get(uri) ?: painter,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.Black),
        contentDescription = "null"
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