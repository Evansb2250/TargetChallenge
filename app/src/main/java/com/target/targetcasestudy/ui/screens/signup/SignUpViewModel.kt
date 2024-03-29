package com.target.targetcasestudy.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<SignUpStates> =
        MutableStateFlow(SignUpStates.Registering())
    val state: StateFlow<SignUpStates> = _state.asStateFlow()


    fun registerUser(
        userName: String,
        password: String,
    ) {
        viewModelScope.launch {
            val response = userRepository.createUser(userName, password)
            _state.value = handleUserRegistrationResponse(response)
        }
    }


    private fun handleUserRegistrationResponse(
        response: AsyncResponse<String>
    ): SignUpStates {
        return when (response) {
            is AsyncResponse.Failed -> SignUpStates.Registering(
                errorState = ErrorState(
                    isError = true,
                    errorMessage = response.message ?: "Couldn't register account"
                )
            )

            is AsyncResponse.Success -> SignUpStates.Registered
        }
    }

    fun reset(){
        _state.value = SignUpStates.Registering()
    }
}