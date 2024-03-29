package com.target.targetcasestudy.di

import com.target.targetcasestudy.core.DispatcherProviderImp
import com.target.targetcasestudy.interfaces.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @Singleton
    @Provides
    fun providesDispatchProvider(): DispatcherProvider = DispatcherProviderImp()
}