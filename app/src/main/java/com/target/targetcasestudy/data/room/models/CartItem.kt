package com.target.targetcasestudy.data.room.models

import androidx.room.Entity
import com.target.targetcasestudy.data.api.models.Price
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem

@Entity(primaryKeys = arrayOf("userId", "dealId", "amountInCents", "currencySymbol"))
data class CartItemEntity(
    val userId: Int,
    val dealId: Int,
    val quantity: Int,
    val title: String,
    val imageUrl: String,
    val fulfillment: String,
    val amountInCents: Int,
    val currencySymbol: String,
    val displayString: String
)


fun CartItemEntity.toCartItem(): CartItem = CartItem(
    userId = this.userId.toString(),
    dealId = this.dealId.toString(),
    quantity = this.quantity,
    title = this.title,
    imageUrl = this.imageUrl,
    fulfillment = this.fulfillment,
    price = Price(
        amountInCents = this.amountInCents,
        currencySymbol = this.currencySymbol,
        displayString = this.displayString,
    )
)
