package com.example.video_editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun scroll() {
    Column(

        modifier = Modifier.fillMaxWidth().padding(top = 300.dp)
    ) {
        val newProgressValue = remember { mutableStateOf(0f) }
        val useNewProgressValue = remember { mutableStateOf(false) }
        Column {
            Slider(
                value = if (useNewProgressValue.value) newProgressValue.value else 2f,
                onValueChange = {  newValue ->
                    useNewProgressValue.value = true
                    newProgressValue.value = newValue },
                onValueChangeFinished = {
                    useNewProgressValue.value = true
                },
            )
            Text(text ="$newProgressValue" )

        }
    }
}