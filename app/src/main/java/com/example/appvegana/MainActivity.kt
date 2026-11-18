package com.example.appvegana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.appvegana.ui.AppNav
import com.example.appvegana.ui.theme.AppVeganaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppVeganaTheme {
                AppNav()
            }
        }
    }
}