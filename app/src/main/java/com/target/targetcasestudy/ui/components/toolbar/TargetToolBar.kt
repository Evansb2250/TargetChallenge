package com.target.targetcasestudy.ui.components.toolbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetToolBar(
    navigationIcon: @Composable () -> Unit = {},
    title: @Composable () -> Unit = {},
    actionIcon: @Composable () -> Unit = {},
    color: Color = Color.White
) {
    TopAppBar(
        modifier = Modifier
            .border(
                BorderStroke(
                    1.dp,
                    color = if (color == Color.White) Color(borderColor) else color
                ), // Change the border stroke color and width
                shape = RectangleShape // adds border only to the bottom part
            ),
        navigationIcon = { navigationIcon.invoke() },
        title = { title.invoke() },
        actions = { actionIcon.invoke() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color,

            ),
    )
}

private val borderColor = 0xFFE0E0E0