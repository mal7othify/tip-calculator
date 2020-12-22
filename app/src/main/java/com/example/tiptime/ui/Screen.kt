package com.example.tiptime.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Screen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Tip Time")
                }
            )
        }
    ) {
        Content()
    }
}


@Preview("Screen Preview")
@Composable
fun ScreenPreview() {
    Screen()
}