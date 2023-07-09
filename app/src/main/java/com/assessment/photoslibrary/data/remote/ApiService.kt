package com.assessment.photoslibrary.data.remote

import com.assessment.photoslibrary.model.response.PhotosListResponse
import com.assessment.photoslibrary.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.RECENT_PHOTOS_URL)
    suspend fun getPhotosList(): Response<PhotosListResponse>
}
