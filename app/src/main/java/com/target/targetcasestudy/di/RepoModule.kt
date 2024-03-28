package com.target.targetcasestudy.di

import androidx.datastore.core.DataStore
import com.target.targetcasestudy.data.repo.UserRepostoryImpl
import com.target.targetcasestudy.data.room.database.TargetDatabase
import com.target.targetcasestudy.interfaces.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.prefs.Preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun providesUserRepository(
        dataStore: DataStore<androidx.datastore.preferences.core.Preferences>,
        database: TargetDatabase,
    ): UserRepository = UserRepostoryImpl(
        userDao = database.userDao(),
        dataStore = dataStore,
    )
}