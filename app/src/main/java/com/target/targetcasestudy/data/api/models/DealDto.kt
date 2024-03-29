package com.target.targetcasestudy.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.target.targetcasestudy.ui.screens.catalog.domain.Deal
import com.target.targetcasestudy.ui.screens.details.domain.DealDetails

@JsonClass(generateAdapter = true)
data class DealDto(
    @field:Json(name = "id") val id: String,

    @field:Json(name = "title") val title: String = "",

    @field:Json(name = "aisle") val aisle: String = "",

    @field:Json(name = "description") val description: String = "",

    @field:Json(name = "image_url") val imageUrl: String = "",

    @field:Json(name = "availability") val availability: String,

    @field:Json(name = "fulfillment") val fulfillment: String,

    @field:Json(name = "regular_price") val regularPrice: Price = default,

    @field:Json(name = "sale_price") val salePrice: Price? = null

)

val default = Price(
    amountInCents = 0,
    currencySymbol = "$",
    displayString = ""
)


fun DealDto.toDealDetail(): DealDetails = DealDetails(
    id = this.id.toInt(),
    title = this.title,
    imageUrl = this.imageUrl,
    description = this.description,
    fulfillment = this.fulfillment,
    salePrice = this.salePrice,
    regularPrice = this.regularPrice
)
fun DealDto.toDeal(): Deal = Deal(
    id = this.id.toInt(),
    title = this.title,
    imageUrl = this.imageUrl,
    aisle = this.aisle,
    fulfillment = this.fulfillment,
    availability = this.availability,
    salePrice = this.salePrice,
    regularPrice = this.regularPrice
)