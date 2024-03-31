package com.target.targetcasestudy.ui.components.textfields

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable

@Composable
fun CustomTextField(
    value: String,
    label: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        label = label,
        onValueChange = onValueChange,
    )
}