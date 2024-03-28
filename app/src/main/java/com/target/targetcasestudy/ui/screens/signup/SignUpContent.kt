package com.target.targetcasestudy.ui.screens.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog

@Preview(
    showBackground = true,
)
@Composable
fun SigUpContent(
    state: SignUpStates = SignUpStates.Registering(),
    signUp: (
        userName: String,
        password: String,
    ) -> Unit = { _, _ -> },
    retry: () -> Unit = {},
    navigateToLoginScreen: () -> Unit = {},
) {

    when (state) {
        SignUpStates.Loading -> {
            LoadingDialog()
        }

        SignUpStates.Registered -> {
            LaunchedEffect(key1 = Unit) {
                navigateToLoginScreen()
            }
        }

        is SignUpStates.Registering -> {
            Box {
                if (state.errorState.isError) {
                    ErrorDialog(
                        title = "Registration Error",
                        error = state.errorState.errorMessage,
                        onDismiss = retry
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    TextField(
                        value = state.userName,
                        onValueChange = { text ->
                            state.userName = text
                        },
                    )

                    TextField(
                        value = state.password,
                        onValueChange = { text ->
                            state.password = text
                        },
                    )

                    TextField(
                        value = state.passwordConfirmation,
                        onValueChange = { text ->
                            state.passwordConfirmation = text
                        },
                    )

                    Button(
                        enabled = state.readyToRegister(),
                        onClick = {
                            signUp(state.userName, state.password)
                        }
                    ) {
                        Text(
                            text = "SIGN IN",
                        )
                    }

                    Button(
                        onClick = navigateToLoginScreen,
                    ) {
                        Text(
                            text = "Cancel",
                        )
                    }
                }
            }
        }
    }
}