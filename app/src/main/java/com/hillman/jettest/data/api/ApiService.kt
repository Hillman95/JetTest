package com.hillman.jettest.data.api

import com.hillman.jettest.data.Constants
import com.hillman.jettest.data.entity.MembersResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("api/v1/channels.members?roomName=general")
    fun getMembersAsync(@HeaderMap headers: Map<String, String?>, @Query("offset") offset:Int, @Query("count") count:Int): Deferred<MembersResponse>

    companion object {
        fun createCorService(): ApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .build()

            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(ApiService::class.java)
        }
    }
}