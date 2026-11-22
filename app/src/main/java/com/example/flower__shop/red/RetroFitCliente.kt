package com.example.flower__shop.red

import com.example.flower__shop.interfaceApi.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetroFitCliente {
    const val BASE_URL = "http://192.168.1.8/flowershop_api/"

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    val apiService: ApiService by lazy {
        val contenType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConfig.asConverterFactory(contenType))
            .build()
            .create(ApiService::class.java)
    }
}