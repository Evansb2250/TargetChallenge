package com.target.targetcasestudy.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    vm: LoginViewModel = hiltViewModel(),
    navigateToSignUpScreen: () -> Unit = {},
    navigateToLandingPage: (userId: String) -> Unit = {},
) {
    DisposableEffect(key1 = Unit) {
        onDispose {
            vm.resetState()
        }
    }

    LaunchedEffect(key1 = Unit){
        vm.loadCredentials()
    }

    LoginContent(
        state = vm.state.collectAsState().value,
        navigateToSignUpScreen = navigateToSignUpScreen,
        navigateToLandingPage = navigateToLandingPage,
        signIn = vm::signIn,
        retry = vm::resetState
    )
}