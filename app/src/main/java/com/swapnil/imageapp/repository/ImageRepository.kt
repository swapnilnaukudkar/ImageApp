package com.swapnil.imageapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.swapnil.imageapp.model.ImageData
import com.swapnil.imageapp.network.ImageApi
import com.swapnil.imageapp.network.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ImageRepository {
    private val TAG = "ImageRepository"
    val getImageData: MutableLiveData<ImageData>
    get() {
        return _imageOfTheDay
    }
    private var _imageOfTheDay: MutableLiveData<ImageData> = MutableLiveData()

    suspend fun getImageOfTheDay(date: String) = withContext(Dispatchers.IO){
        async {
            val result = Retrofit.getClient().create(ImageApi::class.java).getImageOfTheDay(date)
            if (result.body() != null) {
                result.body()?.let {

                    _imageOfTheDay.postValue(result.body())
                }
            } else {
                Log.e(TAG, "getImageOfTheDay: Error: " + result)
            }
        }

    }.await()
}