package com.target.targetcasestudy.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.R
import com.target.targetcasestudy.core.targetLogoDescription

@Composable
fun LoginScreen(
    vm: LoginViewModel = hiltViewModel(),
    navigateToSignUpScreen: () -> Unit = {},
    navigateToLandingPage: (userId: String) -> Unit = {},
) {
    LoginContent(
        state = vm.state.collectAsState().value,
        navigateToSignUpScreen = navigateToSignUpScreen,
        signIn = vm::signIn,
        retry = vm::reset
    )
}


@Preview(showBackground = true)
@Composable
fun LoginContent(
    state: LoginScreenStates = LoginScreenStates.InProgress(),
    navigateToSignUpScreen: () -> Unit = {},
    navigateToLandingPage: (userId: String) -> Unit = {},
    retry: () -> Unit = {},
    signIn: (userName: String, password: String) -> Unit = { _, _ -> },
) {

    when (state) {
        is LoginScreenStates.InProgress -> {
            Box {
                if (state.errorState.isError) {
                    ErrorDialog(
                        title = "Login Error",
                        error = state.errorState.errorMessage,
                        onDismiss = retry
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Image(
                        modifier = Modifier.size(120.dp),
                        painter = painterResource(
                            id = R.drawable.target_logo_32
                        ),
                        contentDescription = targetLogoDescription
                    )


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

                    Button(
                        onClick = {
                            signIn(state.userName, state.password)
                        }
                    ) {
                        Text(
                            text = "SIGN IN",
                        )
                    }

                    Button(
                        onClick = navigateToSignUpScreen,
                    ) {
                        Text(
                            text = "REGISTER",
                        )
                    }

                }
            }

        }

        LoginScreenStates.Loading -> {
            LoadingDialog()
        }

        is LoginScreenStates.Login -> {
            LaunchedEffect(key1 = Unit) {
                navigateToLandingPage(state.userId.toString())
            }
        }
    }
}