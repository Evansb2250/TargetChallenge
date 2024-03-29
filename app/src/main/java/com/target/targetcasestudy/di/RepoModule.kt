package com.target.targetcasestudy.di

import androidx.datastore.core.DataStore
import com.target.targetcasestudy.data.api.DealApiService
import com.target.targetcasestudy.data.repo.ProductRepositoryImp
import com.target.targetcasestudy.data.repo.UserRepostoryImpl
import com.target.targetcasestudy.data.room.database.TargetDatabase
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.interfaces.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun providesUserRepository(
        dataStore: DataStore<androidx.datastore.preferences.core.Preferences>,
        database: TargetDatabase,
        dispatcherProvider: DispatcherProvider,
    ): UserRepository = UserRepostoryImpl(
        userDao = database.userDao(),
        dataStore = dataStore,
        dispatcherProvider = dispatcherProvider,
    )

    @Singleton
    @Provides
    fun providesProductRepository(
        productApiService: DealApiService,
        dispatcherProvider: DispatcherProvider,
        database: TargetDatabase,
    ): ProductRepository =
        ProductRepositoryImp(
            productService = productApiService,
            dispatcherProvider = dispatcherProvider,
            dealsDao = database.dealsDao(),
        )
}