package com.target.targetcasestudy.core

 sealed class AsyncResponse<T> {
    data class Success<T>(val data: T): AsyncResponse<T>()
    data class Failed<T>(val data: T? = null, val message: String? = null): AsyncResponse<T>()
}