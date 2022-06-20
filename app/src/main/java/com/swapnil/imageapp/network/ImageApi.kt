package com.swapnil.imageapp.network

import com.swapnil.imageapp.model.ImageData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageApi {
    @GET("/planetary/apod")
    suspend fun getImageOfTheDay(
        @Query("date") date: String,
    ) : Response<ImageData>
}