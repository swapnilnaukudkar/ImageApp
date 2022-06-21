package com.swapnil.imageapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.swapnil.imageapp.model.ImageData
import com.swapnil.imageapp.repository.ImageRepository
import com.swapnil.imageapp.room.getDatabase
import kotlinx.coroutines.launch

/**
 * [MainActivityViewModel]: ViewModel for MainActivity
 */
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "MainActivityViewModel"

    var showProgressBar: MutableLiveData<Boolean>
    var repository: ImageRepository
    init {
        repository = ImageRepository(getDatabase(application))
        showProgressBar = MutableLiveData();
        showProgressBar.value =false
    }
    val imageOfTheDay: MutableLiveData<ImageData>
        get() {
            return repository.getImageData as MutableLiveData<ImageData>
        }


    fun getImageOfTheDay(dateData: String) {
        viewModelScope.launch {
            showProgressBar.value = true
            repository.getImageOfTheDay(dateData)
            showProgressBar.value = false
        }
    }
}