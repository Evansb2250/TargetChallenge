package com.target.targetcasestudy.interfaces

import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.data.api.models.DealDto
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem
import com.target.targetcasestudy.ui.screens.details.domain.DealDetails

interface ProductRepository {
    suspend fun fetchProducts(): AsyncResponse<List<DealDto>>
    suspend fun fetchProductDetails(id: String): AsyncResponse<DealDetails>

    suspend fun clearCart(userId: String)

    suspend fun getCartCount(userId: String):Int

    suspend fun addDealToCart(cartItem: CartItem): AsyncResponse<String>
}