package com.target.targetcasestudy.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import com.target.targetcasestudy.R
import com.target.targetcasestudy.core.targetLogoDescription

@Composable
fun LoginScreen(
    vm: LoginViewModel = hiltViewModel(),
    navigateToSignUpScreen: () -> Unit = {},
    navigateToLandingPage: (userId: String) -> Unit = {},
) {
    LoginContent(
        state = vm.state,
        navigateToSignUpScreen = navigateToSignUpScreen
    )
}


@Preview(showBackground = true)
@Composable
fun LoginContent(
    state: LoginScreenStates = LoginScreenStates.InProgress(),
    navigateToSignUpScreen: () -> Unit = {},
    signIn: (userName: String, password: String) -> Unit = { _, _ -> },
) {

    when(state){
        is LoginScreenStates.InProgress -> {
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
                    onClick = { /*TODO*/ }) {
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
        LoginScreenStates.Loading -> TODO()
        is LoginScreenStates.Login -> TODO()
    }

}