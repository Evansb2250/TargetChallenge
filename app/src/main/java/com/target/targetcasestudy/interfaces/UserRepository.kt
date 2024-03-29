package com.target.targetcasestudy.interfaces

import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.data.room.models.UserEntity

interface UserRepository {
   suspend fun getUser(
        userName: String,
        password: String,
    ) : AsyncResponse<UserEntity?>

    suspend fun createUser(
        userName: String,
        password: String,
    ): AsyncResponse<String>
    suspend  fun deleteUser(
        userId: String,
    )

    suspend fun authenticateUserId(userId: Int): AsyncResponse<UserEntity?>

    suspend fun getCurrentUserId(): AsyncResponse<UserEntity?>

    suspend fun logout()


}