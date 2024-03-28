package com.target.targetcasestudy.ui.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.ErrorState
import com.target.targetcasestudy.data.room.models.UserEntity
import com.target.targetcasestudy.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private var _state: MutableStateFlow<LoginScreenStates> = MutableStateFlow(
        LoginScreenStates.InProgress()
    )
    val state = _state.asStateFlow()

    fun loadCredentials() {

    }

    fun signIn(
        userName: String,
        password: String,
    ) {
        viewModelScope.launch {
            val response: AsyncResponse<UserEntity?> = userRepository.getUser(userName, password)
            _state.value = handleLoginResponse(response)
        }
    }


    private fun handleLoginResponse(
        response: AsyncResponse<UserEntity?>
    ): LoginScreenStates {
        return when (response) {
            is AsyncResponse.Failed -> {
                LoginScreenStates.InProgress(
                    errorState = ErrorState(
                        isError = true,
                        errorMessage = response.message ?: "Failed login",
                    ),
                )
            }

            is AsyncResponse.Success -> {
                LoginScreenStates.Login(userId = response.data!!.userId)
            }
        }
    }

    fun reset() {
        _state.value = LoginScreenStates.InProgress()
    }
}