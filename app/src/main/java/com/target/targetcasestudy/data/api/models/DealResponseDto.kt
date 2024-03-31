package com.target.targetcasestudy.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DealResponseDto(
  @Json(name = "products")
  val deals: List<DealDto>
)
