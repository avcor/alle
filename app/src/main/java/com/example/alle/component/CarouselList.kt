package com.example.alle.component

import android.net.Uri
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.alle.LocalImageResponse
import com.example.alle.calculate
import com.example.alle.ui.theme.AlleTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CarouselList(imagesList: List<Uri>, fn: (num: Int) -> Unit) {

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
                var bool = remember { mutableStateOf(true) }

                Box(modifier = Modifier
                    .width(60.dp)
                    .onGloballyPositioned {
                        val b = calculate(density, screenMid, it)
                        bool.value = b;
                        if (b) fn(index)
                    }
                    .clickable {
                        scrollToIndex.value = index
                    }
                )
                {
                    GlideImage(
                        model = imagesList[index],
                        contentDescription = null,
                        modifier = Modifier
                            .width(46.dp)
                            .scale(if (bool.value) 1.4f else 1f)
                            .align(Alignment.Center),
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

