package com.target.targetcasestudy.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.target.targetcasestudy.data.room.dao.DealsDao
import com.target.targetcasestudy.data.room.dao.UserDao
import com.target.targetcasestudy.data.room.models.CartItemEntity
import com.target.targetcasestudy.data.room.models.UserEntity

@Database(
    entities = arrayOf(UserEntity::class, CartItemEntity::class),
    version = 3
)
abstract class TargetDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun dealsDao(): DealsDao
}
