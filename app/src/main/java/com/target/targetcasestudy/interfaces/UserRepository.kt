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
    )
    suspend  fun deleteUser(
        userName: String,
    )

    suspend fun logout()


}