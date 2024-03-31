package com.target.targetcasestudy.data.api

import com.target.targetcasestudy.data.api.models.DealDto
import com.target.targetcasestudy.data.api.models.DealResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DealApiService {
    @GET("deals")
    suspend fun retrieveDeals(): DealResponseDto

    @GET("deals/{dealId}")
    suspend fun retrieveDeal(@Path("dealId") dealId: String): DealDto
}
