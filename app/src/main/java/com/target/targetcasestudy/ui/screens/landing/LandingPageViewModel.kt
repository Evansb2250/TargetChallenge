package com.target.targetcasestudy.ui.screens.landing

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.data.room.models.UserEntity
import com.target.targetcasestudy.data.room.models.toUser
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private var _state: MutableStateFlow<LandingScreenStates> = MutableStateFlow(LandingScreenStates.Loading)
    val state = _state.asStateFlow()

    fun loadDetails(
        userId: Int,
    ) {
        viewModelScope.launch(dispatcherProvider.main) {
            val response = userRepository.authenticateUserId(userId)
            _state.value = handleAuthenticationResponse(response)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _state.update { LandingScreenStates.Logout }
        }
    }

    fun deleteUser(
        userId: String,
    ) {
        viewModelScope.launch(dispatcherProvider.main) {
            productRepository.deleteUserCartItems(userId)
            userRepository.deleteUser(userId)
            _state.update {
                LandingScreenStates.Logout
            }
        }
    }


    private suspend fun handleAuthenticationResponse(
        response: AsyncResponse<UserEntity?>
    ): LandingScreenStates {
        return when (response) {
            is AsyncResponse.Failed -> LandingScreenStates.Logout
            is AsyncResponse.Success -> {
                if (response.data != null) {
                    val cartCount = productRepository.getCartCount(response.data.userId.toString())
                    LandingScreenStates.LoggedIn(
                        currentUser = response.data.toUser(),
                        cartItems = cartCount
                    )
                } else {
                    LandingScreenStates.Logout
                }
            }
        }
    }
}