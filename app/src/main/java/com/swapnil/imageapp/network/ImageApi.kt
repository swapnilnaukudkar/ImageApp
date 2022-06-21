package com.swapnil.imageapp.network

import com.swapnil.imageapp.model.ImageData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [ImageApi]: Retrofit api interface
 *          List of queries to fet data from server
 */

interface ImageApi {
    @GET("/planetary/apod")
    suspend fun getImageOfTheDay(
        @Query("date") date: String,
    ) : Response<ImageData>
}