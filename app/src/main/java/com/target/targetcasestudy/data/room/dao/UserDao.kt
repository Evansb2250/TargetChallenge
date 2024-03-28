package com.target.targetcasestudy.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.target.targetcasestudy.data.room.models.UserEntity

@Dao
interface UserDao {
    @Insert(
        onConflict = OnConflictStrategy.IGNORE,
    )
   suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM USERENTITY WHERE userId=:userId")
    suspend fun retrieveUser(
        userId: Int
    ): UserEntity?

    @Query("SELECT * FROM USERENTITY WHERE LOWER(userName) = LOWER(:userName) AND LOWER(password) = LOWER(:password)")
    suspend  fun retrieveUser(
        userName: String,
        password: String,
    ): UserEntity?

    @Query("SELECT * FROM  USERENTITY WHERE LOWER(userName) = LOWER(:userName)")
    suspend  fun userExist(
        userName: String,
    ): UserEntity?

    @Delete
    suspend  fun deleteUser(user: UserEntity)
}