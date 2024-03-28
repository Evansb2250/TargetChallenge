package com.target.targetcasestudy.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.data.room.dao.UserDao
import com.target.targetcasestudy.data.room.models.UserEntity
import com.target.targetcasestudy.interfaces.UserRepository

import javax.inject.Inject

class UserRepostoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val dataStore: DataStore<Preferences>,
) : UserRepository {
    override suspend fun getUser(
        userName: String,
        password: String,
    ): AsyncResponse<UserEntity?> {
        val potentialUser = userDao.retrieveUser(userName, password)
        return if (potentialUser != null) {
            AsyncResponse.Success(data = potentialUser)
        } else {
            val errorMessage =
                if (userDao.userExist(userName) != null ) "Wrong Credentials" else "User doesn't exist"

            AsyncResponse.Failed(
                message = errorMessage
            )
        }
    }

    override suspend fun createUser(
        userName: String,
        password: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(
        userName: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}