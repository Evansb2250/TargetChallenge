package com.target.targetcasestudy.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.R
import com.target.targetcasestudy.core.targetLogoDescription
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue18
import com.target.targetcasestudy.theme.primaryColor
import com.target.targetcasestudy.ui.components.textfields.CustomTextField
import com.target.targetcasestudy.ui.components.textfields.PasswordTextField

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
                        modifier = Modifier
                            .padding(dpValue18)
                            .size(120.dp),
                        painter = painterResource(
                            id = R.drawable.target_logo_32
                        ),
                        contentDescription = targetLogoDescription
                    )

                    Text(
                        fontFamily = RebotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(32f, TextUnitType.Sp),
                        color = primaryColor,
                        text = "Target",
                        textAlign = TextAlign.Center,
                    )

                    Column(
                        modifier = Modifier.padding(
                            top = 20.dp,
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {

                        CustomTextField(
                            value = state.userName,
                            label = { Text(text = "Username") },
                            onValueChange = { text ->
                                state.userName = text
                            }
                        )
                        PasswordTextField(
                            value = state.password,
                            label = { Text(text = "Password") },
                            hidePassword = state.hidePassword,
                            onValueChange = { text ->
                                state.password = text
                            },
                            onPasswordVisibilityChange = { it ->
                                state.hidePassword = it
                            }
                        )


                        OutlinedButton(
                            onClick = {
                                signIn(state.userName, state.password)
                            }
                        ) {
                            Text(
                                color = primaryColor,
                                text = "SIGN IN",
                            )
                        }

                        OutlinedButton(
                            onClick = navigateToSignUpScreen,
                        ) {
                            Text(
                                color = primaryColor,
                                text = "REGISTER",
                            )
                        }

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