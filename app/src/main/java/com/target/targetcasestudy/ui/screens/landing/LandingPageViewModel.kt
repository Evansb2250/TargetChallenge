package com.target.targetcasestudy.ui.screens.landing

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private var _state: MutableStateFlow<LandingScreenStates> =
        MutableStateFlow(LandingScreenStates.Loading)
    val state = _state.asStateFlow()

    fun loadDetails(
        userId: Int,
    ) {
        viewModelScope.launch {
            val response = userRepository.authenticateUserId(userId)
            _state.value = handleAuthenticationResponse(response)
        }
    }

    private fun handleAuthenticationResponse(
        response: AsyncResponse<String>
    ): LandingScreenStates {
        return when (response) {
            is AsyncResponse.Failed -> LandingScreenStates.Logout
            is AsyncResponse.Success -> LandingScreenStates.LoggedIn(response.data)
        }
    }
}