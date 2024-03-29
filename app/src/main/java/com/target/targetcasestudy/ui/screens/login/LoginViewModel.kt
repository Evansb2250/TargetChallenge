package com.target.targetcasestudy.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.data.room.models.UserEntity
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private var _state: MutableStateFlow<LoginScreenStates> = MutableStateFlow(
        LoginScreenStates.InProgress()
    )
    val state = _state.asStateFlow()

    //Used to get previous credentials
    fun loadCredentials() {
        viewModelScope.launch(dispatcherProvider.main) {
            val potentialCurrentUserId = userRepository.getCurrentUserId()
            when (potentialCurrentUserId) {
                //Do Nothing
                is AsyncResponse.Failed -> {}
                is AsyncResponse.Success -> {
                    //skips login screen if user never logged out
                    _state.update {
                        handleLoginResponse(potentialCurrentUserId)
                    }
                }
            }
        }
    }

    fun signIn(
        userName: String,
        password: String,
    ) {
        viewModelScope.launch(dispatcherProvider.main) {

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

    fun resetState() {
        _state.value = LoginScreenStates.InProgress()
    }
}