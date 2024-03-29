package com.target.targetcasestudy.core.domain

import androidx.room.PrimaryKey

data class User(
    val userId: String,
    val userName: String,
    val password: String,
)
