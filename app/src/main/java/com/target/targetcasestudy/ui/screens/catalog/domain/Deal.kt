package com.target.targetcasestudy.ui.screens.catalog.domain

import com.target.targetcasestudy.data.api.models.Price

data class Deal(
    val id: Int,
    val title: String,
    val imageUrl:String,
    val aisle: String,
    val fulfillment: String,
    val availability: String,
    val salePrice: Price? = null,
    val regularPrice: Price,
)


