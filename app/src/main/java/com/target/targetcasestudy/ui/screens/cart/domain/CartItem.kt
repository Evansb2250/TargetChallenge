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