package com.assessment.photoslibrary.viewmodel.list

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.photoslibrary.data.Repository
import com.assessment.photoslibrary.model.DogResponse
import com.assessment.photoslibrary.model.response.PhotosListResponse
import com.assessment.photoslibrary.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _response: MutableLiveData<NetworkResult<PhotosListResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<PhotosListResponse>> = _response

    private val _downloadResponse: MutableLiveData<Boolean> = MutableLiveData()
    val downloadResponse = _downloadResponse

    fun fetchPhotosResponse() = viewModelScope.launch {
        repository.getPictureList().collect { values ->
            _response.value = values
        }
    }


    fun downloadImage(bitmap: Bitmap, dir: File, fileName: String) {

        viewModelScope.launch {
            repository.saveImage(bitmap, dir, fileName).collect { value ->
                _downloadResponse.value = value
            }
        }
    }

}