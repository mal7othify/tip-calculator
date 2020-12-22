package com.example.tiptime

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.toArgb
import com.example.tiptime.ui.Screen
import com.example.tiptime.ui.TipTimeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                window.statusBarColor = colors.primaryVariant.toArgb()
                // A surface container using the 'background' color from the theme
                Surface(color = colors.background) {
                    Screen()
                }
            }
        }
    }
}