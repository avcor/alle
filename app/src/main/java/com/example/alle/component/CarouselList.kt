package com.example.alle.component

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.alle.calculateOnRedMark
import com.example.alle.datastructure.FixedLimitStack
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Composable
fun CarouselList(
    imagesList: List<Uri>,
    preBuildImage: FixedLimitStack<Uri,
            Painter?>,
    fnOnRedMark: (num: Uri) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .height(40.dp)
    ) {
        val density = LocalDensity.current
        val configuration = LocalConfiguration.current
        val screenMid = configuration.screenWidthDp.dp / 2
        val scrollState = rememberLazyListState()
        val scrollToIndex = remember { mutableIntStateOf(-1) }

        LaunchedEffect(scrollToIndex.value) {
            if (scrollToIndex.value != -1) {
                scrollState.animateScrollToItem(scrollToIndex.value)
            }
        }

        LazyRow(
            state = scrollState,
            contentPadding = PaddingValues(horizontal = screenMid - 10.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(1f)
        ) {
            items(imagesList.size) { index ->
                val uri = imagesList[index]
                val bool = remember { mutableStateOf(true) }

                Box(modifier = Modifier
                    .width(60.dp)
                    .onGloballyPositioned {
                        val b = calculateOnRedMark(density, screenMid, it)
                        bool.value = b
                        if (b) fnOnRedMark(uri)
                    }
                    .clickable { scrollToIndex.value = index }
                )
                {
                    val p = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(uri)
                            .size(Size.ORIGINAL)
                            .build()
                    )
                    p.state.painter?.let {
                        preBuildImage.addIfNotContain(uri,it)
                    }

                    Image(
                        painter = p,
                        contentDescription = null,
                        modifier = Modifier
                            .width(46.dp)
                            .scale(if (bool.value) 1.4f else 1f)
                            .align(Alignment.Center)
                            .clip(shape = RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.Crop,
                    )

                }
            }
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(60.dp)
                .align(Alignment.BottomCenter)
                .background(Color.Red)
        )
    }
}

