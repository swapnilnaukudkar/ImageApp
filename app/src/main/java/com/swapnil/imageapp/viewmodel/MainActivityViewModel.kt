package com.swapnil.imageapp.viewmodel

import androidx.lifecycle.*
import com.swapnil.imageapp.model.ImageData
import com.swapnil.imageapp.repository.ImageRepository
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val TAG = "MainActivityViewModel"
    private var repo : ImageRepository
    var showProgressBar: MutableLiveData<Boolean>

    init {
        showProgressBar = MutableLiveData();
        showProgressBar.value =false
        repo = ImageRepository()
    }
    val imageOfTheDay: MutableLiveData<ImageData>
        get() {
            return repo.getImageData
        }


    fun getImageOfTheDay(dateData: String) {
        viewModelScope.launch {
            showProgressBar.value = true
            repo.getImageOfTheDay(dateData)
            showProgressBar.value = false
        }
    }
}