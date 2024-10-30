package com.example.myapplication.MainScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.ui.PlayerView
import com.example.myapplication.components.PlayerControls
import com.example.myapplication.components.SimpleMediaPlayerUI
import com.example.myapplication.service.SimpleMediaServiceHandler
import com.example.myapplication.service.VideoItem
import com.example.myapplication.ui.theme.SimpleMediaViewModel
import com.example.myapplication.ui.theme.UIState


@Composable
internal fun SimpleMediaScreen(
    vm: SimpleMediaViewModel,
fr:List<VideoItem>
    ) {

    var isServiceRunning = true

    val state = vm.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (state.value) {
            UIState.Initial -> CircularProgressIndicator(
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
            )
            is UIState.Ready -> {
                LaunchedEffect(true) { // This is only call first time
                    if (isServiceRunning) {

                        isServiceRunning = false
                    }
                }

                ReadyContent(vm = vm,fr)
            }
        }

    }
}

@Composable
private fun ReadyContent(
    vm: SimpleMediaViewModel,
fr: List<VideoItem>
    ) {

    val selectVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let(vm::addVideoUri)
        }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SimpleMediaPlayerUI(
            durationString = vm.formatDuration(vm.duration),
            playResourceProvider = {
                if (vm.isPlaying) android.R.drawable.ic_media_pause
                else android.R.drawable.ic_media_play
            },
            progressProvider = { Pair(vm.progress, vm.progressString) },
            onUiEvent = vm::onUIEvent,
        )
        AndroidView(
            factory = { context ->
                PlayerView(context).also {
                    it.player = vm.player
                }.apply {
                    useController = false
                }
            }
        )
        IconButton(onClick = {
            selectVideoLauncher.launch("video/mp4")
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Select video"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(fr) { item ->
                Text(
                    text = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            vm.playVideo(item.contentUri)
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}
