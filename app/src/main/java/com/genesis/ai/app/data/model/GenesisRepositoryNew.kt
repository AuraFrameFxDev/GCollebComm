package com.genesis.ai.app.data.model

import com.genesis.ai.app.data.api.ApiService
import com.genesis.ai.app.data.model.AskResponse
import com.genesis.ai.app.data.model.ImportResponse
import com.genesis.ai.app.data.model.MessageRequest
import com.genesis.ai.app.data.model.MessageResponse
import com.genesis.ai.app.data.model.RootToggleRequest
import com.genesis.ai.app.data.model.RootToggleResponse
import com.genesis.ai.app.data.model.SyncRequest
import com.genesis.ai.app.data.model.SyncResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Multipart
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File
import java.util.concurrent.TimeUnit

object GenesisRepositoryNew {
    // Set this to true to use mock responses
    private const val USE_MOCK_RESPONSES = false

    // Base URL for the API - can be changed in app settings
    private var baseUrl = "http://10.0.2.2:8000/"  // Default to local development server

    // Mock responses
    private val mockResponses = listOf(
        "I'm currently running in offline mode. The server appears to be down.",
        "This is a mock response. Please check your internet connection.",
        "The Genesis AI service is currently unavailable. Using local responses.",
        "I can still help you with basic tasks while offline."
    )
    
    // API instance caching
    private var apiInstance: ApiService? = null

    private fun createApiInstance(): ApiService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val api: ApiService
        get() = if (USE_MOCK_RESPONSES) {
            object : ApiService {
                override fun sendMessage(@Body message: MessageRequest): Call<MessageResponse> =
                    createMockCall {
                        MessageResponse(
                            message = mockResponses.random(),
                            status = "success"
                        )
                    }

                @Multipart
                override fun importFile(@Part file: MultipartBody.Part): Call<ImportResponse> =
                    createMockCall { ImportResponse(status = "success") }

                override fun toggleRoot(@Body request: RootToggleRequest): Call<RootToggleResponse> =
                    createMockCall { RootToggleResponse("success") }

                override fun getAiQuestions(): Call<AskResponse> =
                    createMockCall {
                        AskResponse(
                            questions = listOf("Question 1", "Question 2", "Question 3"),
                            status = "success"
                        )
                    }
                
                // Task synchronization method
                override fun syncTasks(@Body request: SyncRequest): Call<SyncResponse> {
                    return createMockCall {
                        SyncResponse(
                            status = "success",
                            message = "Tasks synchronized successfully (mock)",
                            syncedTasks = emptyList(),
                            serverTime = System.currentTimeMillis()
                        )
                    }
                }

                private inline fun <T> createMockCall(crossinline response: () -> T): Call<T> {
                    return object : Call<T> {
                        override fun execute(): Response<T> = Response.success(response())
                        override fun enqueue(callback: Callback<T>) {
                            callback.onResponse(this, Response.success(response()))
                        }

                        override fun isExecuted(): Boolean = false
                        override fun cancel() {}
                        override fun isCanceled(): Boolean = false
                        override fun request(): Request =
                            Request.Builder().url("https://mock.url").build()

                        override fun clone(): Call<T> = this
                        override fun timeout() = object : Timeout() {
                            override fun timeout(timeout: Long, unit: TimeUnit): Timeout = this
                            override fun deadlineNanoTime(deadlineNanoTime: Long): Timeout = this
                        }
                    }
                }
            }
        } else {
            apiInstance ?: createApiInstance().also { apiInstance = it }
        }

    /**
     * Updates the base URL for API requests and recreates the API instance
     * @param newUrl The new base URL to use for API requests
     * @return Boolean indicating if the URL was updated
     */
    fun updateBaseUrl(newUrl: String): Boolean {
        return if (baseUrl != newUrl) {
            baseUrl = newUrl.trimEnd('/') + "/"  // Ensure proper URL format
            apiInstance = null  // Force recreation of API instance on next access
            true
        } else {
            false
        }
    }
}