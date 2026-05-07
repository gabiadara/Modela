package com.modela.app.data.api

import com.modela.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ModelaApi {

    @GET("models")
    suspend fun getModels(@Query("category") category: String? = null): Response<List<ModelProfile>>

    @GET("models/featured")
    suspend fun getFeaturedModels(): Response<List<ModelProfile>>

    @GET("models/trending")
    suspend fun getTrendingModels(): Response<List<ModelProfile>>

    @GET("models/{id}")
    suspend fun getModelById(@Path("id") id: String): Response<ModelProfile>

    @GET("models/search")
    suspend fun searchModels(@Query("q") query: String): Response<List<ModelProfile>>

    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("chat/conversations")
    suspend fun getConversations(): Response<List<ChatConversation>>

    @GET("chat/{conversationId}/messages")
    suspend fun getMessages(@Path("conversationId") id: String): Response<List<ChatMessage>>

    @POST("chat/{conversationId}/messages")
    suspend fun sendMessage(@Path("conversationId") id: String, @Body message: ChatMessage): Response<ChatMessage>

    @GET("models/{id}/reviews")
    suspend fun getReviews(@Path("id") modelId: String): Response<List<Review>>
}
