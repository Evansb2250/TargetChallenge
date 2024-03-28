package com.target.targetcasestudy.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    val userId: Int = 0,
    val userName: String,
    val password: String,
)