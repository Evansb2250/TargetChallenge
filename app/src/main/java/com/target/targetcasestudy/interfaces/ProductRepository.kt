package com.target.targetcasestudy.interfaces

import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.data.api.models.DealDto
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem
import com.target.targetcasestudy.ui.screens.details.domain.DealDetails


interface ProductRepository {
    /**
     * [fetchProductDetails] makes a network call to get all the products from server
     */
    suspend fun fetchProducts(): AsyncResponse<List<DealDto>>

    /**
     * [fetchProductDetails] makes a network call to get the details of 1 deal including deal descriptions
     */
    suspend fun fetchProductDetails(id: String): AsyncResponse<DealDetails>

    suspend fun getCartCount(userId: String): Int

    /**
     * [getCartItems] retrieves all cartItems stored from Room
     */
    suspend fun getCartItems(id: String): AsyncResponse<List<CartItem>>

    /**
     * [addDealToCart] stores cart Item into Room
     */
    suspend fun addDealToCart(cartItem: CartItem): AsyncResponse<String>

    suspend fun deleteCartItem(cartItem: CartItem): AsyncResponse<String>

    //Used when deleting an account
    suspend fun deleteUserCartItems(userId: String): AsyncResponse<String>
}