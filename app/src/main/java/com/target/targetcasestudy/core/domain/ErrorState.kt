package com.target.targetcasestudy.core.domain

data class ErrorState(
    val isError: Boolean = false,
    val errorMessage: String = "",
)
