package com.target.targetcasestudy.ui.screens.cart

import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem

sealed class CartScreenStates() {
    object Loading : CartScreenStates()
    data class CartView(
        val products: List<CartItem>,
        val cartTotalInPennies: Int,
        val errorState: ErrorState = ErrorState()
    ) : CartScreenStates()

    object Error: CartScreenStates()
}