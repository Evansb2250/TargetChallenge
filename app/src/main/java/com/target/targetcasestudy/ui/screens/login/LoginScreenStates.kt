package com.target.targetcasestudy.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.target.targetcasestudy.core.ErrorState

sealed class LoginScreenStates {
    object Loading: LoginScreenStates()
    data class InProgress(
        private val initialUserName: String = "",
        private val initialPassword: String = "",
        private val errorState: ErrorState = ErrorState(),
    ): LoginScreenStates(){
        var userName by  mutableStateOf(initialUserName)
        var password by mutableStateOf(initialPassword)
    }

    data class Login(
        val userId: String,
    ) : LoginScreenStates()
}



