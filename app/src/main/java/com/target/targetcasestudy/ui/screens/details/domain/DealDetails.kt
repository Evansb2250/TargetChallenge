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