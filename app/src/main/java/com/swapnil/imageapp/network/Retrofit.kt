package com.swapnil.imageapp.network

import android.util.Log
import com.swapnil.imageapp.config.AppConfig
import com.swapnil.imageapp.config.AppConfig.IO_TIMEOUT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * [Retrofit]: Get Retrofit Instance
 */
object Retrofit {

    fun getClient(baseUrl: String = AppConfig.BASE_URL): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(IO_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(IO_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(IO_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor {
                        addApiKeyToRequests(it)
                    }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url()
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", AppConfig.apiKey).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }

    fun Throwable.printRetrofitError() {
        this.printStackTrace()
        when (this) {
            is IOException -> Log.e(
                this::class.java.simpleName,
                "Network Error happened in Retrofit | cause: ${this.cause} | message: ${this.message}"
            )
            is HttpException -> Log.e(
                this::class.java.simpleName,
                "HTTP Exception happened in Retrofit | cause: ${this.cause} | message: ${this.message}"
            )
            else -> Log.e(
                this::class.java.simpleName,
                "Unknown Error happened in Retrofit | cause: ${this.cause} | message: ${this.message}"
            )
        }
    }
}