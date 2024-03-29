package com.target.targetcasestudy.ui.components.toolbar

import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetToolBar(
    title: @Composable () -> Unit = {},
    actionIcon: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = { title.invoke() },
        actions = { actionIcon.invoke() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
    )
    HorizontalDivider()
}