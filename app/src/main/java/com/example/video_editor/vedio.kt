package com.example.video_editor

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerExo() {

    val context = LocalContext.current

    /** Create the player and set the media item with clipping configuration */
    val player = ExoPlayer.Builder(context).build().apply {
        setMediaItem(
            MediaItem.Builder()
                .setUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                .setClippingConfiguration(
                    MediaItem.ClippingConfiguration.Builder()
                        .setStartPositionMs(200000) // Start at 10 seconds
                        .setEndPositionMs(600000) // End at 20 seconds
                        .build()
                )
                .build()
        )
        prepare()
        playWhenReady
    }

    val playerView = PlayerView(context)
    playerView.player = player

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = { playerView }
    )

    /** Cleanup the player when the composable is disposed of */
    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }
}