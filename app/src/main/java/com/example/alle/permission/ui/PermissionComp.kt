package com.example.alle.permission.ui

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.alle.DECLINED_TEXT
import com.example.alle.R
import com.example.alle.RATIONAL_TEXT
import com.example.alle.openAppSettings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestMyPermission(fn: (bool: Boolean) -> Unit) {
    val TAG = "abcd"
    val textValue = remember { mutableStateOf("") }
    val context = LocalContext.current
    val permissionState = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    val filePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            val showRational = ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            Log.d(TAG, "callback  ${showRational}")
            if (!isGranted) {
                if (showRational) {
                    //dismissed
                    textValue.value = RATIONAL_TEXT
                } else {
                    //declined
                    textValue.value = DECLINED_TEXT
                }
            }else{
                fn(isGranted)
            }
        }
    )

    LaunchedEffect(key1 = Unit) {
        filePermissionResultLauncher.launch(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    LaunchedEffect(key1 = permissionState.status.isGranted) {
        fn(permissionState.status.isGranted)
    }


    if (!permissionState.status.isGranted) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 30.dp)
        ) {
            Text(
                text = textValue.value,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 30.dp)
            )
            Button(onClick = {
                val showRational = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                if (showRational && !permissionState.status.isGranted)
                    filePermissionResultLauncher.launch(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                else
                    context.openAppSettings()

            }) {
                Text(text = "Grant Read permission")
            }
        }
    }

}