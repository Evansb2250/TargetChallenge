package com.target.targetcasestudy

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.target.targetcasestudy.nav.TargetNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.actionBar?.hide()
        // Retrieve the color value from resources
        val statusBarColor = getColor(R.color.colorPrimary)
        this.window.statusBarColor = statusBarColor

        setContent {
            App()
        }
    }
}

@Composable
private fun App() {
    TargetNavHost()
}