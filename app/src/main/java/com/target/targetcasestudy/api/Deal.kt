package com.target.targetcasestudy.api

data class Deal(
  val id: String,

  val title: String,

  val aisle: String,

  val description: String,

  val regularPrice: Price,

  val salePrice: Price
)
