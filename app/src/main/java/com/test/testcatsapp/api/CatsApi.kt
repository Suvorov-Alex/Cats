package com.test.testcatsapp.api

import com.test.testcatsapp.api.entity.Cat
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatsApi {

    @GET("/v1/images/search")
    fun getCats(
        @Header(X_API_KEY_HEADER) header: String,
        @Query("limit") limit: Int?,
        @Query("page") page: Int?,
        @Query("order") order: String?
    ): Single<List<Cat>>

    companion object {
        private const val X_API_KEY_HEADER = "x-api-key"
    }
}