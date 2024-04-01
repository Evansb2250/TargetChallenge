package com.target.targetcasestudy.ui.screens.details.domain

import com.target.targetcasestudy.data.api.models.Price

data class DealDetails(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val description: String,
    val fulfillment: String,
    val salePrice: Price? = null,
    val regularPrice: Price,
)

val dummyDealDetails = DealDetails(
    id = 1,
    title = "Dummy Deal",
    imageUrl = "https://example.com/image.jpg",
    description = "This is a dummy deal description.",
    fulfillment = "Online",
    salePrice = Price(999, "$", "$9.99"),
    regularPrice = Price(1299, "$","$12.99")
)