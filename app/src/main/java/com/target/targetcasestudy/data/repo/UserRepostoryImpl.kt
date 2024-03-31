package com.target.targetcasestudy.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.DataStoreKeys.CURRENT_USER_ID
import com.target.targetcasestudy.core.serverErrorMessage
import com.target.targetcasestudy.core.nonExistentUserError
import com.target.targetcasestudy.core.userAlreadyExistError
import com.target.targetcasestudy.data.room.dao.UserDao
import com.target.targetcasestudy.data.room.models.UserEntity
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

import javax.inject.Inject

class UserRepostoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val dataStore: DataStore<Preferences>,
    private val dispatcherProvider: DispatcherProvider,
) : UserRepository {
    override suspend fun getUser(
        userName: String,
        password: String,
    ): AsyncResponse<UserEntity?> {
        return withContext(dispatcherProvider.io) {
            try {
                val potentialUser = userDao.retrieveUser(userName, password)
                verifyRealUser(potentialUser,userName)
            } catch (e: Exception) {
                AsyncResponse.Failed(
                    message = e.message ?: serverErrorMessage
                )
            }
        }
    }

    private suspend fun verifyRealUser(
        poentialUser: UserEntity?,
        userName: String
    ): AsyncResponse<UserEntity?> {
        return if (poentialUser != null) {
            storeCurrentUserId(poentialUser.userId)
            AsyncResponse.Success(data = poentialUser)
        } else {
            val errorMessage =
                if (userDao.userExist(userName) != null) "Wrong Credentials" else nonExistentUserError
            AsyncResponse.Failed(
                message = errorMessage
            )
        }
    }

    override suspend fun getCurrentUserId(): AsyncResponse<UserEntity?> {
        return withContext(dispatcherProvider.io) {
            try {
                val prefs = dataStore.data.firstOrNull() ?: return@withContext AsyncResponse.Failed(
                    data = null,
                    message = "no preference file"
                )
                val userId = prefs[CURRENT_USER_ID] ?: return@withContext AsyncResponse.Failed(
                    data = null,
                    message = "no user"
                )
                val potentialUser = userDao.retrieveUser(userId = userId.toInt())

                return@withContext if (potentialUser != null) {
                    AsyncResponse.Success(data = potentialUser)
                } else {
                    AsyncResponse.Failed(data = null, message = "no user")
                }
            } catch (e: NumberFormatException) {
                return@withContext AsyncResponse.Failed(data = null, message = "no user")
            }
        }
    }

    private suspend fun storeCurrentUserId(
        userId: Int
    ) {
        dataStore.edit {
            it[CURRENT_USER_ID] = userId.toString()
        }
    }

    override suspend fun createUser(
        userName: String,
        password: String,
    ): AsyncResponse<String> {
        return withContext(dispatcherProvider.io) {
            try {
                if (userDao.userExist(userName) == null) {
                    userDao.insertUser(
                        user = UserEntity(
                            userName = userName,
                            password = password,
                        )
                    )
                    AsyncResponse.Success("User created!!")
                } else {
                    AsyncResponse.Failed(userAlreadyExistError)
                }
            } catch (e: Exception) {
                AsyncResponse.Failed(userAlreadyExistError)
            }
        }
    }

    override suspend fun deleteUser(
        userId: String,
    ) {
        withContext(dispatcherProvider.io) {
            try {
                val user = userDao.retrieveUser(userId = userId.toInt()) ?: return@withContext
                userDao.deleteUser(user)
            } catch (e: NumberFormatException) {
                //Do Nothing
            }
        }
    }

    override suspend fun authenticateUserId(userId: Int): AsyncResponse<UserEntity?> {
        return withContext(dispatcherProvider.io) {
            try {
                val user = userDao.retrieveUser(userId)
                if (user != null) {
                    AsyncResponse.Success(data = user)
                } else {
                    AsyncResponse.Failed(message = nonExistentUserError)
                }
            } catch (e: Exception) {
                AsyncResponse.Failed(message = e.message ?: serverErrorMessage)
            }
        }
    }

    override suspend fun logout() {
        dataStore.edit {
            it[CURRENT_USER_ID] = ""
        }
    }
}