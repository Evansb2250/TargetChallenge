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
                if (userDao.userExist(userName) != null) "Wrong Credentials" else "User doesn't exist"

            AsyncResponse.Failed(
                message = errorMessage
            )
        }
    }

    override suspend fun createUser(
        userName: String,
        password: String,
    ): AsyncResponse<String> {
        return if (userDao.userExist(userName) == null) {
            userDao.insertUser(
                user = UserEntity(
                    userName = userName,
                    password = password,
                )
            )
            AsyncResponse.Success("User created!!")
        } else {
            AsyncResponse.Failed("User already exist!!")
        }
    }

    override suspend fun deleteUser(
        userName: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun authenticateUserId(userId: Int): AsyncResponse<String> {
        return if (userDao.retrieveUser(userId) != null) {
            AsyncResponse.Success(data = "$userId")
        } else {
            AsyncResponse.Failed(message = "User doesn't exist")
        }
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}