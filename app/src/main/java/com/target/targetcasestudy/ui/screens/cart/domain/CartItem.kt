package com.target.targetcasestudy.ui.screens.cart.domain

import com.target.targetcasestudy.data.api.models.Price
import com.target.targetcasestudy.data.room.models.CartItemEntity

data class CartItem(
    val userId: String,
    val dealId: String,
    val quantity: Int,
    val title: String,
    val imageUrl: String,
    val fulfillment: String,
    val price: Price,
)

val dummyCartItem = CartItem(
    userId = "user123",
    dealId = "deal456",
    quantity = 2,
    title = "Dummy Product",
    imageUrl = "https://example.com/product.jpg",
    fulfillment = "Online",
    price = Price(
        amountInCents = 999,
        currencySymbol = "$",
        displayString = "$9.99"
    )
)

fun CartItem.toCartItemEntity(): CartItemEntity = CartItemEntity(
    userId = this.userId.toInt(),
    dealId = this.dealId.toInt(),
    quantity = this.quantity,
    title = this.title,
    imageUrl = this.imageUrl,
    fulfillment = this.fulfillment,
    amountInCents = this.price.amountInCents,
    currencySymbol = this.price.currencySymbol,
    displayString = this.price.displayString,
)