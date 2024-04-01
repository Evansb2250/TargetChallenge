package com.target.targetcasestudy.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.target.targetcasestudy.core.domain.User

@Entity
class UserEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    val userId: Int = 0,
    val userName: String,
    val password: String,
)

val dummyUserEntity = UserEntity(
    userId = 0,
    userName = "entity",
    password = "password",
)

fun UserEntity.toUser(): User = User(
    userId = this.userId.toString(),
    userName = this.userName,
    password = this.password,
)