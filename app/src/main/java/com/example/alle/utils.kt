package com.example.alle

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

fun calculateOnRedMark(density: Density, screenMid: Dp, coordinates: LayoutCoordinates): Boolean {

    val densityValue = density.density
    val pxFromStart = coordinates.localToRoot(Offset(0f, 0f)).x
    val dpFromStart = (pxFromStart / densityValue).dp

    val e = dpFromStart + 60.dp
    val s = dpFromStart

    return (e > screenMid && screenMid > s)
}
