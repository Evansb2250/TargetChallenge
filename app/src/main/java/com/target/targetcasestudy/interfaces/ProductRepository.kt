package com.target.targetcasestudy.interfaces

interface ProductRepository {
    fun fetchProducts(): String
    fun fetchProductDetails(id: String): String
}