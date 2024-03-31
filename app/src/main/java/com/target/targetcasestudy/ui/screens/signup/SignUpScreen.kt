package com.target.targetcasestudy.ui.screens.signup

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignUpScreen(
    vm: SignUpViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit = {},
) {
    BackHandler {
        navigateToLoginScreen()
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            vm.reset()
        }
    }

    SigUpContent(
        state = vm.state.collectAsState().value,
        signUp = vm::registerUser,
        retry = vm::reset,
        navigateToLoginScreen = navigateToLoginScreen,
    )
}


