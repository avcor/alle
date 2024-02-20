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

fun calculate(density: Density, screenMid: Dp, coordinates: LayoutCoordinates): Boolean {
    var dpFromStart = 0.dp
    var dpFromEnd = 0.dp

    val densityValue = density.density
    val pxFromStart = coordinates.localToRoot(Offset(0f, 0f)).x
    val pxFromEnd = with(density) {
        coordinates.localToRoot(
            Offset(
                0f,
                0f
            )
        ).x + coordinates.size.width
    }
    dpFromStart = (pxFromStart / densityValue).dp
    dpFromEnd = (pxFromEnd / densityValue).dp

    val e = dpFromStart + 60.dp
    val s = dpFromStart

    return (e > screenMid && screenMid > s)
}
