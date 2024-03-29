package com.target.targetcasestudy.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Price(
 @field:Json(name = "amount_in_cents")  val amountInCents: Int,
 @field:Json(name = "currency_symbol")   val currencySymbol: String,
 @field:Json(name = "display_string")    val displayString: String
)
