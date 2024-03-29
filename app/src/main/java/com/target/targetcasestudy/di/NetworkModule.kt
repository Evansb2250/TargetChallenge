package com.target.targetcasestudy.di

import com.target.targetcasestudy.data.api.DealApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofi(): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun providesDealApiRxService(
        retrofit: Retrofit,
    ): DealApiService = retrofit.create(DealApiService::class.java)

}