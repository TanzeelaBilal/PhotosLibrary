package com.assessment.photoslibrary.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getPictures(page: Int) =
        apiService.getRecentPhotos(page = page)

}