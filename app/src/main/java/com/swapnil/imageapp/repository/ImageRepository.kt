package com.swapnil.imageapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.swapnil.imageapp.model.ImageData
import com.swapnil.imageapp.network.ImageApi
import com.swapnil.imageapp.network.Retrofit
import com.swapnil.imageapp.room.ImageDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * [ImageRepository] : this class is used to fetch data from local or remote depending upon conditions.
 *                  Get data from server and save to Room DB once data get updated to db room will notify for data change.
 */

class ImageRepository(private val imageDatabase: ImageDatabase) {
    private val TAG = "ImageRepository"
    val getImageData: LiveData<ImageData>
        get() {
            return _imageOfTheDay
        }

    private var _imageOfTheDay: MutableLiveData<ImageData> = MutableLiveData()

    suspend fun getImageOfTheDay(date: String) = withContext(Dispatchers.IO) {
        async {
            val result = Retrofit.getClient().create(ImageApi::class.java).getImageOfTheDay(date)
            if (result.body() != null) {
                result.body()?.let {

                    // Save data to local DB and get notified on db update
                    /* _imageOfTheDay = imageDatabase.imageDao().getImage(date)
                    imageDatabase.imageDao().insertImageData(result.body()!!)
                     */
                    _imageOfTheDay.postValue(result.body())
                }
            } else {
                // in server data error fetch from local DB
                /*
                _imageOfTheDay = imageDatabase.imageDao().getImage(date);
                 */
                Log.e(TAG, "getImageOfTheDay: Error: " + result)
            }
        }

    }.await()
}