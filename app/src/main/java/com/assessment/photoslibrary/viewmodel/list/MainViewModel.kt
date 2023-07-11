package com.assessment.photoslibrary.viewmodel.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.photoslibrary.data.Repository
import com.assessment.photoslibrary.model.response.PhotosListResponse
import com.assessment.photoslibrary.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _response: MutableLiveData<NetworkResult<PhotosListResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<PhotosListResponse>> = _response

    fun fetchPhotosResponse(currentPage : Int) = viewModelScope.launch {
        repository.getPictureList(currentPage).collect { values ->
            _response.value = values
        }
    }

}