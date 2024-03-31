package com.target.targetcasestudy.ui.components.textfields

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.target.targetcasestudy.R

@Composable
fun PasswordTextField(
    value: String,
    label: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    hidePassword: Boolean = true,
    onPasswordVisibilityChange: (Boolean) -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        label = label,
        onValueChange = onValueChange,
        visualTransformation = if (hidePassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            val imageRes =
                if (hidePassword) R.drawable.hide_password_icon else R.drawable.show_password_icon
            Image(
                modifier = Modifier.clickable {
                    onPasswordVisibilityChange(!hidePassword)
                },
                painter = painterResource(
                    id = imageRes,
                ),
                contentDescription = "visibility Image",
            )
        }
    )
}