package com.example.video_editor

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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

@OptIn(UnstableApi::class) @Composable
fun VideoPlayerExo() {

    val context = LocalContext.current
    /**Create the player and add in the context **/
    val player = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(  "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"))

    }

    val playerView = PlayerView(context)

    /**Attach the player to a view**/
    playerView.player = player

    LaunchedEffect(player) {
        player.prepare()
        player.playWhenReady

    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = {
            playerView
        })

}