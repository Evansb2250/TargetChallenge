package com.target.targetcasestudy.ui.screens.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.target.targetcasestudy.theme.dpValue120
import com.target.targetcasestudy.theme.dpValue18
import com.target.targetcasestudy.theme.primaryColor
import com.target.targetcasestudy.ui.components.textfields.CustomTextField
import com.target.targetcasestudy.ui.components.textfields.PasswordTextField

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
    val context = LocalContext.current
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

                    Image(
                        modifier = Modifier
                            .padding(dpValue18)
                            .size(dpValue120),
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
                        text = "Register",
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
                            onValueChange = state::userName::set,
                        )

                        PasswordTextField(
                            value = state.password,
                            label = { Text(text = "Password") },
                            onValueChange = state::password::set,
                            hidePassword = state.hidePassword,
                            onPasswordVisibilityChange = state::hidePassword::set
                        )

                        PasswordTextField(
                            value = state.passwordConfirmation,
                            label = { Text(text = "Password Confirmation") },
                            onValueChange = state::passwordConfirmation::set,
                            hidePassword = state.hidePasswordConfirm,
                            onPasswordVisibilityChange = state::hidePasswordConfirm::set
                        )

                        if (state.containsInvalidPasswords()) {
                            Text(
                                color = Color.Red,
                                text = "Password must match",
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                if(!state.readyToRegister()){
                                    Toast.makeText(context, "Please fill out everything", Toast.LENGTH_SHORT).show()
                                }else{
                                    signUp(state.userName, state.password)
                                }
                            }
                        ) {
                            Text(
                                color = primaryColor,
                                text = "Register",
                            )
                        }

                        OutlinedButton(
                            onClick = navigateToLoginScreen,
                        ) {
                            Text(
                                color = primaryColor,
                                text = "Cancel",
                            )
                        }
                    }
                }
            }
        }
    }
}