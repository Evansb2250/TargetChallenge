package com.target.targetcasestudy.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _state: MutableStateFlow<CartScreenStates> = MutableStateFlow(CartScreenStates.Loading)
    val state = _state.asStateFlow()

    private lateinit var _userId: String

    fun setCartId(userId: String) {
        _userId = userId
    }

    fun loadCartData() {
        viewModelScope.launch(dispatcherProvider.main) {
            if (::_userId.isInitialized) {

                val response: AsyncResponse<List<CartItem>> =
                    productRepository.getCartItems(_userId)

                _state.update {
                    handleResponse(response)
                }

            } else {
                _state.update { createErrorState("userId not found") }
            }
        }
    }

    private fun createErrorState(message: String): CartScreenStates.CartView =
        CartScreenStates.CartView(
            products = emptyList(),
            cartTotalInPennies = 0,
            errorState = ErrorState(
                isError = true,
                errorMessage = message,
            )
        )

    fun deletCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            when (productRepository.deleteCartItem(cartItem)) {
                is AsyncResponse.Failed -> {
                    //Do Nothing
                }
                is AsyncResponse.Success -> {
                    _state.update {
                        handleResponse(productRepository.getCartItems(_userId))
                    }
                }
            }
        }
    }


    private fun handleResponse(
        response: AsyncResponse<List<CartItem>>
    ): CartScreenStates.CartView {
        return when (response) {
            is AsyncResponse.Failed -> {
                createErrorState(response.message ?: "failed to delete item")
            }

            is AsyncResponse.Success -> {
                CartScreenStates.CartView(
                    products = response.data,
                    cartTotalInPennies = response.data.sumOf { it.quantity * it.price.amountInCents },
                )
            }
        }
    }

    fun onDismissErrorDialog() {
        _state.update {
            CartScreenStates.Error
        }
    }
}