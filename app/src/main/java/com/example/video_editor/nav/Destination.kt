package com.example.video_editor.nav

sealed class Destination(val route: String) {
    object Main: Destination("main")
    object Secondary: Destination("secondary")
}