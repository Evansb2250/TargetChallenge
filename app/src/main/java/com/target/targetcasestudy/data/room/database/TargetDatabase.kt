package com.target.targetcasestudy.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.target.targetcasestudy.data.room.dao.UserDao
import com.target.targetcasestudy.data.room.models.UserEntity

@Database(
    entities = arrayOf(UserEntity::class),
    version = 2
)
abstract class TargetDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private lateinit var instance: TargetDatabase

        fun getDatabase(
            context: Context
        ): TargetDatabase {
            if (!Companion::instance.isInitialized) {
                val database = Room.databaseBuilder(
                    context,
                    TargetDatabase::class.java,
                    "TargetDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance = database
            }
            return instance
        }
    }
}
