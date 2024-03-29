package com.target.targetcasestudy.di

import android.content.Context
import androidx.room.Room
import com.target.targetcasestudy.data.room.database.TargetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesTargetDatabase(
        @ApplicationContext context: Context,
    ): TargetDatabase = Room.databaseBuilder(
        context,
        TargetDatabase::class.java,
        "TargetDatabase"
    ).fallbackToDestructiveMigration()
        .build()


}