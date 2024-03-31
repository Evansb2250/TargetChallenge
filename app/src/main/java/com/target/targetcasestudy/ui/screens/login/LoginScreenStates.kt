package com.target.targetcasestudy.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.target.targetcasestudy.core.domain.ErrorState

sealed class LoginScreenStates {
    object Loading: LoginScreenStates()
    data class InProgress(
        private val initialUserName: String = "",
        private val initialPassword: String = "",
        val errorState: ErrorState = ErrorState(),
    ): LoginScreenStates(){
        var userName by  mutableStateOf(initialUserName)
        var password by mutableStateOf(initialPassword)
        var hidePassword by mutableStateOf(true)

    }

    data class Login(
        val userId: Int,
    ) : LoginScreenStates()
}



