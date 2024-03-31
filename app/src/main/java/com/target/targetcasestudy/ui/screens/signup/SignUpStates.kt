package com.target.targetcasestudy.ui.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.target.targetcasestudy.core.domain.ErrorState

sealed class SignUpStates {
    object Loading : SignUpStates()
    data class Registering(
        private val initialUserName: String = "",
        private val initialPassword: String = "",
        val errorState: ErrorState = ErrorState(),
    ) : SignUpStates() {
        var userName by mutableStateOf(initialUserName)
        var password by mutableStateOf(initialPassword)
        var passwordConfirmation by mutableStateOf(initialPassword)
        var hidePassword by mutableStateOf(true)
        var hidePasswordConfirm by mutableStateOf(true)
        fun readyToRegister(): Boolean {
            return try {
                require(!userName.isEmpty())
                require(!password.isEmpty() && password.equals(passwordConfirmation))
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }

        fun containsInvalidPasswords(): Boolean {
            return !password.isEmpty() &&
                    !passwordConfirmation.isEmpty() &&
                    !password.equals(passwordConfirmation)
        }

    }


    object Registered : SignUpStates()
}
